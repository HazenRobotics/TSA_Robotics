package org.firstinspires.ftc.teamcode.teleOP;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.atomic.AtomicReference;

@Config
@Autonomous(name="FtcDashboard Camera", group="Linear")
public class FtcDashboard_Camera extends LinearOpMode {

    public static Rect perpendicular = new Rect(200,100,450,250);
    public static Rect parallel = new Rect(240,400,420,470);
    //Talk Volatile vs AtomicReference, final to make sure it is only initialized once, etc.
    public static class CameraStreamProcessor implements VisionProcessor, CameraStreamSource{
        private final AtomicReference<Bitmap> lastFrame = new AtomicReference<>(Bitmap.createBitmap(1,1, Bitmap.Config.RGB_565));
        private AtomicReference<Bitmap> editFrame = new AtomicReference<>(Bitmap.createBitmap(1,1,Bitmap.Config.RGB_565));
        //private volatile Bitmap lastFrame =  Bitmap.createBitmap(1,1, Bitmap.Config.RGB_565);
        @Override
        public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
            continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
        }

        @Override
        public void init(int width, int height, CameraCalibration cameraCalibration) {
            lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        }

        @Override
        public Object processFrame(Mat frame, long captureTimeNanos) {
            Imgproc.rectangle(frame, new Point(perpendicular.x,perpendicular.y), new Point(perpendicular.width, perpendicular.height), new Scalar(0,255,0) , 4);
            Imgproc.rectangle(frame, new Point(parallel.x,parallel.y), new Point(parallel.width, parallel.height), new Scalar(255,0,0) , 4);
            Bitmap b = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.RGB_565);
            Utils.matToBitmap(frame, b);
            lastFrame.set(b);

//                        Bitmap b_edit = Bitmap.createBitmap(frame.width(),frame.height(),Bitmap.Config.ARGB_8888);
//            Utils.matToBitmap(frame, b_edit);
//            editFrame.set(b_edit);
            return null;

        }
        public Bitmap getProcessedFrame(){
            return editFrame.get();
        }

        @Override
        public void onDrawFrame(Canvas canvas, int i, int i1, float v, float v1, Object o) {
            Paint color = new Paint();
            color.setColor(Color.GREEN);
            color.setStyle(Paint.Style.STROKE);

            color.setStrokeWidth(v1 * 4);

            android.graphics.Rect drawRectangle = makeGraphicsRect(perpendicular,v);

            canvas.drawRect(drawRectangle, color);
        }

        private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx){
            int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
            int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
            int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
            int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);
            return new android.graphics.Rect(left, top, right, bottom);
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        final CameraStreamProcessor processor = new CameraStreamProcessor();

        new VisionPortal.Builder().
                addProcessor(processor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();
        waitForStart();

        FtcDashboard.getInstance().startCameraStream(processor, 0);

        while(opModeIsActive()){
//            FtcDashboard.getInstance().sendImage(processor.getProcessedFrame());
            sleep(100);
        }
    }
}
