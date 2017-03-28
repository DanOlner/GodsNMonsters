package godsnmonsters;

import processing.core.*;

import KinectPV2.KJoint;
import KinectPV2.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class GodsNMonsters extends PApplet {

    /*
Thomas Sanchez Lengeling.
 http://codigogenerativo.com/

 KinectPV2, Kinect for Windows v2 library for processing

 Skeleton color map example.
 Skeleton (x,y) positions are mapped to match the color Frame
     */
    public static KinectPV2 kinect;
    public static ArrayList<KSkeleton> kinectSkeletons;
    //All skelekons that will be drawn, either via kinect or data.
    //Each has persistence and various attached features.
//    public static ArrayList<SkeletonWrapper> skeletons;

    //Use kinect's colour index as map for kinect skeletons 
    public static HashMap<Integer, SkeletonWrapper> skeletons;

//    PShape bot;
    public static boolean saveDataFromSkeleton1 = false;
    public static boolean saveDataToFileOnNextFrame = false;

    public static boolean loadDataForSkeleton1 = false;

    //tests
    PImage img1, img2;

    public void setup() {

        kinect = new KinectPV2(this);

        kinect.enableSkeletonColorMap(true);
//  kinect.enableColorImg(true);
//  kinect.enableInfraredImg(true);
//        kinect.enableInfraredLongExposureImg(true);

        kinect.init();

        skeletons = new HashMap<Integer, SkeletonWrapper>();

        textSize(45);

//        blendMode(DIFFERENCE);
//        frameRate(8);
    }

    public void draw() {
        
        background(0);
                
//        fill(0,100);
//        rect(0,0,width);
//        fill(0);

        //check for new / persisting / dead skeletons before getting data
        kinectSkeletons = kinect.getSkeletonColorMap();

        //if not loading a test skeleton
        if (!loadDataForSkeleton1) {

            //If any found, get their indices
            for (KSkeleton kskeleton : kinectSkeletons) {

                if (kskeleton.isTracked()) {

                    //if this is an existing skeleton, update its data
                    if (skeletons.containsKey(kskeleton.getIndexColor())) {

//                    System.out.println("existing kskeleton being updated. Index: " + kskeleton.getIndexColor());
                        skeletons.get(kskeleton.getIndexColor()).setSkeletonData(kskeleton);

                        //Else it's new and needs adding to the current list of skeletons
                    } else {

                        createNewKSkeleton(kskeleton);

                    }

                }//if isTracked

            }//foreach

            //if!loadDataForSkeleton1
        } else {

            //create new skeleton from data
            createNewSkeletonFromData("data/skeletonData/test1.csv");

        }

        //If no skeletons from kskeletons got updated... 
        //they're no longer being detected and need to be dropped.
        //"I still exist" is set to false at the end of the frame
        //And re-set to true if data found again for it above.
        //Any still false at this point need removing.
        for (Map.Entry<Integer, SkeletonWrapper> pair : skeletons.entrySet()) {
            if (!pair.getValue().isStillAlive()) {
                System.out.println("removing kskeleton. Index: " + pair.getKey() + ", length of skeletons array: " + skeletons.size());
                skeletons.remove(pair.getKey());
            }
        }

        //this is also where each skeleton has its "I'm still alive" set to false.
        //it'll be reset back to true in the next frame if data is added to it.
        for (SkeletonWrapper pete : skeletons.values()) {
            pete.drawSkeleton();
        }

        fill(255, 0, 0);
//        text(frameRate, 50, 50);
        
//        saveFrame("C://SaveFrames/GodsNMonsters/####.png");

    }

    private void createNewKSkeleton(KSkeleton kskeleton) {

        System.out.println("new kskeleton being wrapped, index: " + kskeleton.getIndexColor());
        skeletons.put(kskeleton.getIndexColor(), new SkeletonWrapper(kskeleton, this));

        //activate storing data for first skeleton
        if (saveDataFromSkeleton1 && skeletons.size() == 1) {

            //quickest way to get first element
//            http://stackoverflow.com/questions/1671378/java-get-first-item-from-a-collection
            skeletons.values().iterator().next().startStoringData();
        }

    }

    private void createNewSkeletonFromData(String fileName) {

        //Data skeletons all have negative index
        int index = (int) (Math.random() * -1000);

        System.out.println("new kskeleton being made from data, index: " + index);
        skeletons.put(index, new SkeletonWrapper(fileName, this));

        loadDataForSkeleton1 = false;

    }

    public void drawOld() {
        background(0);

//  image(kinect.getInfraredImage(), 0, 0, width, height);
//        image(kinect.getInfraredLongExposureImage(), 0, 0, width, height);
//  image(kinect.getColorImage(), 0, 0, width, height);
        kinectSkeletons = kinect.getSkeletonColorMap();

        //individual JOINTS
        for (int i = 0; i < kinectSkeletons.size(); i++) {

            KSkeleton skeleton = (KSkeleton) kinectSkeletons.get(i);
            if (skeleton.isTracked()) {
                KJoint[] joints = skeleton.getJoints();

                //always 26
//                System.out.println("size of joints: " + joints.length);
                int col = skeleton.getIndexColor();

                //System.out.println(skeleton.getIndexColor());
                fill(col);
                stroke(col);
                drawBody(joints);

                //draw different color for each hand state
                drawHandState(joints[KinectPV2.JointType_HandRight]);
                drawHandState(joints[KinectPV2.JointType_HandLeft]);
            }
        }

        fill(255, 0, 0);
        text(frameRate, 50, 50);

    }

//DRAW BODY
    public void drawBody(KJoint[] joints) {
        drawBone(joints, KinectPV2.JointType_Head, KinectPV2.JointType_Neck);
        drawBone(joints, KinectPV2.JointType_Neck, KinectPV2.JointType_SpineShoulder);
        drawBone(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_SpineMid);
        drawBone(joints, KinectPV2.JointType_SpineMid, KinectPV2.JointType_SpineBase);
        drawBone(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_ShoulderRight);
        drawBone(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_ShoulderLeft);
        drawBone(joints, KinectPV2.JointType_SpineBase, KinectPV2.JointType_HipRight);
        drawBone(joints, KinectPV2.JointType_SpineBase, KinectPV2.JointType_HipLeft);

        // Right Arm
        drawBone(joints, KinectPV2.JointType_ShoulderRight, KinectPV2.JointType_ElbowRight);
        drawBone(joints, KinectPV2.JointType_ElbowRight, KinectPV2.JointType_WristRight);
        drawBone(joints, KinectPV2.JointType_WristRight, KinectPV2.JointType_HandRight);
        drawBone(joints, KinectPV2.JointType_HandRight, KinectPV2.JointType_HandTipRight);
        drawBone(joints, KinectPV2.JointType_WristRight, KinectPV2.JointType_ThumbRight);

        // Left Arm
        drawBone(joints, KinectPV2.JointType_ShoulderLeft, KinectPV2.JointType_ElbowLeft);
        drawBone(joints, KinectPV2.JointType_ElbowLeft, KinectPV2.JointType_WristLeft);
        drawBone(joints, KinectPV2.JointType_WristLeft, KinectPV2.JointType_HandLeft);
        drawBone(joints, KinectPV2.JointType_HandLeft, KinectPV2.JointType_HandTipLeft);
        drawBone(joints, KinectPV2.JointType_WristLeft, KinectPV2.JointType_ThumbLeft);

        // Right Leg
        drawBone(joints, KinectPV2.JointType_HipRight, KinectPV2.JointType_KneeRight);
        drawBone(joints, KinectPV2.JointType_KneeRight, KinectPV2.JointType_AnkleRight);
        drawBone(joints, KinectPV2.JointType_AnkleRight, KinectPV2.JointType_FootRight);

        // Left Leg
        drawBone(joints, KinectPV2.JointType_HipLeft, KinectPV2.JointType_KneeLeft);
        drawBone(joints, KinectPV2.JointType_KneeLeft, KinectPV2.JointType_AnkleLeft);
        drawBone(joints, KinectPV2.JointType_AnkleLeft, KinectPV2.JointType_FootLeft);

        drawJoint(joints, KinectPV2.JointType_HandTipLeft);
        drawJoint(joints, KinectPV2.JointType_HandTipRight);
        drawJoint(joints, KinectPV2.JointType_FootLeft);
        drawJoint(joints, KinectPV2.JointType_FootRight);

        drawJoint(joints, KinectPV2.JointType_ThumbLeft);
        drawJoint(joints, KinectPV2.JointType_ThumbRight);

        drawJoint(joints, KinectPV2.JointType_Head);

    }

//draw joint
    public void drawJoint(KJoint[] joints, int jointType) {
        pushMatrix();
        translate(joints[jointType].getX(), joints[jointType].getY(), joints[jointType].getZ());
        ellipse(0, 0, 25, 25);
        popMatrix();
    }

//draw bone
    public void drawBone(KJoint[] joints, int jointType1, int jointType2) {
        pushMatrix();
        translate(joints[jointType1].getX(), joints[jointType1].getY(), joints[jointType1].getZ());
        ellipse(0, 0, 25, 25);
        popMatrix();
        line(joints[jointType1].getX(), joints[jointType1].getY(), joints[jointType1].getZ(), joints[jointType2].getX(), joints[jointType2].getY(), joints[jointType2].getZ());
    }

//draw hand state
    public void drawHandState(KJoint joint) {
        noStroke();
        handState(joint.getState());
        pushMatrix();
        translate(joint.getX(), joint.getY(), joint.getZ());
        ellipse(0, 0, 70, 70);
        popMatrix();
//        System.out.println("Hand z: " + joint.getZ());

    }

    public void drawBody2(Joint[] joints) {
        drawBone2(joints, KinectPV2.JointType_Head, KinectPV2.JointType_Neck);
        drawBone2(joints, KinectPV2.JointType_Neck, KinectPV2.JointType_SpineShoulder);
        drawBone2(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_SpineMid);
        drawBone2(joints, KinectPV2.JointType_SpineMid, KinectPV2.JointType_SpineBase);
        drawBone2(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_ShoulderRight);
        drawBone2(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_ShoulderLeft);
        drawBone2(joints, KinectPV2.JointType_SpineBase, KinectPV2.JointType_HipRight);
        drawBone2(joints, KinectPV2.JointType_SpineBase, KinectPV2.JointType_HipLeft);

        // Right Arm
        drawBone2(joints, KinectPV2.JointType_ShoulderRight, KinectPV2.JointType_ElbowRight);
        drawBone2(joints, KinectPV2.JointType_ElbowRight, KinectPV2.JointType_WristRight);
        drawBone2(joints, KinectPV2.JointType_WristRight, KinectPV2.JointType_HandRight);
        drawBone2(joints, KinectPV2.JointType_HandRight, KinectPV2.JointType_HandTipRight);
        drawBone2(joints, KinectPV2.JointType_WristRight, KinectPV2.JointType_ThumbRight);

        // Left Arm
        drawBone2(joints, KinectPV2.JointType_ShoulderLeft, KinectPV2.JointType_ElbowLeft);
        drawBone2(joints, KinectPV2.JointType_ElbowLeft, KinectPV2.JointType_WristLeft);
        drawBone2(joints, KinectPV2.JointType_WristLeft, KinectPV2.JointType_HandLeft);
        drawBone2(joints, KinectPV2.JointType_HandLeft, KinectPV2.JointType_HandTipLeft);
        drawBone2(joints, KinectPV2.JointType_WristLeft, KinectPV2.JointType_ThumbLeft);

        // Right Leg
        drawBone2(joints, KinectPV2.JointType_HipRight, KinectPV2.JointType_KneeRight);
        drawBone2(joints, KinectPV2.JointType_KneeRight, KinectPV2.JointType_AnkleRight);
        drawBone2(joints, KinectPV2.JointType_AnkleRight, KinectPV2.JointType_FootRight);

        // Left Leg
        drawBone2(joints, KinectPV2.JointType_HipLeft, KinectPV2.JointType_KneeLeft);
        drawBone2(joints, KinectPV2.JointType_KneeLeft, KinectPV2.JointType_AnkleLeft);
        drawBone2(joints, KinectPV2.JointType_AnkleLeft, KinectPV2.JointType_FootLeft);

        drawJoint2(joints, KinectPV2.JointType_HandTipLeft);
        drawJoint2(joints, KinectPV2.JointType_HandTipRight);
        drawJoint2(joints, KinectPV2.JointType_FootLeft);
        drawJoint2(joints, KinectPV2.JointType_FootRight);

        drawJoint2(joints, KinectPV2.JointType_ThumbLeft);
        drawJoint2(joints, KinectPV2.JointType_ThumbRight);

        drawJoint2(joints, KinectPV2.JointType_Head);

    }

//draw joint
    public void drawJoint2(Joint[] joints, int jointType) {
        pushMatrix();
        translate(joints[jointType].getX(), joints[jointType].getY(), joints[jointType].getZ());
        ellipse(0, 0, 25, 25);

//        text(jointType, 10,0);
        popMatrix();
    }

//draw bone
    public void drawBone2(Joint[] joints, int jointType1, int jointType2) {
        pushMatrix();
        translate(joints[jointType1].getX(), joints[jointType1].getY(), joints[jointType1].getZ());
        ellipse(0, 0, 25, 25);
        popMatrix();
        line(joints[jointType1].getX(), joints[jointType1].getY(), joints[jointType1].getZ(), joints[jointType2].getX(), joints[jointType2].getY(), joints[jointType2].getZ());
    }

//draw hand state
    public void drawHandState2(Joint joint) {
        noStroke();
        handState(joint.getState());
        pushMatrix();
        translate(joint.getX(), joint.getY(), joint.getZ());
        ellipse(0, 0, 70, 70);
        popMatrix();
//        System.out.println("Hand z: " + joint.getZ());

    }

    /*
Different hand state
 KinectPV2.HandState_Open
 KinectPV2.HandState_Closed
 KinectPV2.HandState_Lasso
 KinectPV2.HandState_NotTracked
     */
    public void handState(int handState) {
        switch (handState) {
            case KinectPV2.HandState_Open:
                fill(0, 255, 0);
                break;
            case KinectPV2.HandState_Closed:
                fill(255, 0, 0);
                break;
            case KinectPV2.HandState_Lasso:
                fill(0, 0, 255);
                break;
            case KinectPV2.HandState_NotTracked:
                fill(255, 255, 255);
                break;
        }
    }

    @Override
    public void keyPressed() {

        if (key == ' ') {

            saveDataToFileOnNextFrame = true;

        }

    }

    public void settings() {
        size(1920, 1080, P3D);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--present", "--window-color=#666666", "--hide-stop", "godsnmonsters.GodsNMonsters"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
