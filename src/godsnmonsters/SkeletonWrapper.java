package godsnmonsters;

import KinectPV2.*;
import KinectPV2.KJoint;
import KinectPV2.KSkeleton;
import java.util.ArrayList;
import processing.core.PVector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Skeleton data from any source: options for direct input from Kinect and from
 * another data source This will also keep state where KSkeleton does not.
 *
 * Setup: Kinect has enumerated refs to each joint type. We can get info on the
 * joint via
 *
 * @author Dan Olner
 */
public class SkeletonWrapper {

    GodsNMonsters p;

    //Skeleton via data. If via data, the object will be persistent between frames.
    //KSkeleton will be reloaded each time
    private SkeletonFromData dskeleton;

    //Always 26 of them, though only 25 used (0 to 24).
    Joint[] joints = new Joint[25];

    ArrayList<BodyPart> bodyParts = new ArrayList<>();

    //still exists on this frame. Will be true when created.
    boolean stillAlive = true;

    //if we're storing data for this skeleton
    ArrayList<Float[]> dataStore;
    int dataIndex = 0;

    boolean storingDataNow = false;
    boolean thisIsaDataSkeleton = false;

    public SkeletonWrapper(KSkeleton kskeleton, GodsNMonsters p) {
        
        this.p = p;

        //initialise joints
        for (int i = 0; i < joints.length; i++) {
            joints[i] = new Joint();
        }

        //get first instance of data and set joints
        setSkeletonData(kskeleton);

        createBodyParts();

    }

    //Create skeleton from data
    public SkeletonWrapper(String fileName, GodsNMonsters p) {

        this.p = p;

        //this is now a data skeleton
        thisIsaDataSkeleton = true;

        dataStore = StaticArrayReadWrite.read2DarrayOfFloats(fileName);

        //initialise joints
        for (int i = 0; i < joints.length; i++) {
            joints[i] = new Joint();
        }

        createBodyParts();

    }

    /**
     * If from kinect, set data with a KSkeleton
     *
     * @param skeleton
     */
    public void setSkeletonData(KSkeleton skeleton) {

//        this.p = p;

        //this skeleton still exists so mark as so, so it's not removed
        setStillAlive(true);

        //wrap kjoints
        KJoint[] kjoints = skeleton.getJoints();

        //Set joints data to match each kjoint. Index will match to kinect enumeration.
        for (int i = 0; i < 25; i++) {
            joints[i].setJoints(kjoints[i]);
        }

    }

    /**
     * Set data if source is file
     */
    public void setSkeletonData() {

        //but if a data skeleton, we do need to get this frame's data parsed
        Float[] jointData = dataStore.get(dataIndex++);

        //back to start if data end reached
        dataIndex = (dataIndex == dataStore.size() ? 0 : dataIndex);

        for (int i = 0; i < 25; i++) {

            joints[i].setX(jointData[i * 5]);
            joints[i].setY(jointData[(i * 5) + 1]);
            joints[i].setZ(jointData[(i * 5) + 2]);
            joints[i].setType(jointData[(i * 5) + 3].intValue());
            joints[i].setState(jointData[(i * 5) + 4].intValue());

        }

    }

    private void createBodyParts() {

        //test bodypart
        BodyPart arms = new BodyPart(p, joints);

        //add bodypiece
        //LEFT FOREARM, ORIGIN AT WRIST, REFERENCE ELBOW. 
        arms.newBodyPiece(new BodyPiece(p, "data/images/alienforearm1.png",
                joints[KinectPV2.JointType_WristLeft], joints[KinectPV2.JointType_ElbowLeft],
                new PVector(11, 40), new PVector(174, 38), false, false));
        //left upper arm, origin at elbow, reference shoulder. 
        arms.newBodyPiece(new BodyPiece(p, "data/images/alienupperarm1.png",
                joints[KinectPV2.JointType_ElbowLeft], joints[KinectPV2.JointType_ShoulderLeft],
                new PVector(26, 31), new PVector(184, 27), false, false));
        //left hand, origin at wrist, reference hand tip. 
        arms.newBodyPiece(new BodyPiece(p, "data/images/alienhand1.png",
                joints[KinectPV2.JointType_WristLeft], joints[KinectPV2.JointType_HandTipLeft],
                new PVector(80,35), new PVector(11,43), false, false));
        
        
        //LEFT FOREARM, ORIGIN AT WRIST, REFERENCE ELBOW. 
        //Mirror the images
        arms.newBodyPiece(new BodyPiece(p, "data/images/alienforearm1.png",
                joints[KinectPV2.JointType_WristRight], joints[KinectPV2.JointType_ElbowRight],
                new PVector(11, 40), new PVector(174, 38), true, false));
        //left upper arm, origin at elbow, reference shoulder. 
        arms.newBodyPiece(new BodyPiece(p, "data/images/alienupperarm1.png",
                joints[KinectPV2.JointType_ElbowRight], joints[KinectPV2.JointType_ShoulderRight],
                new PVector(26, 31), new PVector(184, 27), true, false));
        //left hand, origin at wrist, reference hand tip. 
        arms.newBodyPiece(new BodyPiece(p, "data/images/alienhand1.png",
                joints[KinectPV2.JointType_WristRight], joints[KinectPV2.JointType_HandTipRight],
                new PVector(80,35), new PVector(11,43), true, false));

        bodyParts.add(arms);
        
        //Legs
        BodyPart legs = new BodyPart(p, joints);

        //add bodypiece
        //LEFT UPPER LEG. 
        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianupperlegthin.png",
                joints[KinectPV2.JointType_HipLeft], joints[KinectPV2.JointType_KneeLeft],
                new PVector(21,19), new PVector(19,306), false, false));
        
        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianlowerlegthin.png",
                joints[KinectPV2.JointType_KneeLeft], joints[KinectPV2.JointType_AnkleLeft],
                new PVector(14,32), new PVector(16,284), false, false));
        
        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianupperlegthin.png",
                joints[KinectPV2.JointType_HipRight], joints[KinectPV2.JointType_KneeRight],
                new PVector(21,19), new PVector(19,306), true, false));
        
        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianlowerlegthin.png",
                joints[KinectPV2.JointType_KneeRight], joints[KinectPV2.JointType_AnkleRight],
                new PVector(14,32), new PVector(16,284), true ,false));
        
        bodyParts.add(legs); 
        
        
        //RIBS
        BodyPart ribs = new BodyPart(p, joints);
        
        ribs.newBodyPiece(new BodyPiece(p, "data/images/ribcage1.png",
                joints[KinectPV2.JointType_SpineShoulder], joints[KinectPV2.JointType_SpineMid],
                new PVector(227,36), new PVector(236,430), false, false));
        
        bodyParts.add(ribs);
        
//        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianupperleg.png",
//                joints[KinectPV2.JointType_HipLeft], joints[KinectPV2.JointType_KneeLeft],
//                new PVector(43,39), new PVector(39,299), false, false));
//        
//        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianlowerleg.png",
//                joints[KinectPV2.JointType_KneeLeft], joints[KinectPV2.JointType_AnkleLeft],
//                new PVector(31,48), new PVector(34,296), false, false));
//        
//        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianupperleg.png",
//                joints[KinectPV2.JointType_HipRight], joints[KinectPV2.JointType_KneeRight],
//                new PVector(43,39), new PVector(39,299), true, false));
//        
//        legs.newBodyPiece(new BodyPiece(p, "data/images/vitruvianlowerleg.png",
//                joints[KinectPV2.JointType_KneeRight], joints[KinectPV2.JointType_AnkleRight],
//                new PVector(31,48), new PVector(34,296), true ,false));
//        
       
    }

    /**
     * get variables for all joints from whatever source we're using.
     */
    private void getJoints() {

    }

    public boolean isStillAlive() {
        return stillAlive;
    }

    public void setStillAlive(boolean stillAlive) {
        this.stillAlive = stillAlive;
    }

    public void drawSkeleton() {

        //First: reset "I still exist" boolean
        //Will set true if data found again in the next frame.
        //if this is a skeleton being drawn from data, we don't need the alive checks
        if (!thisIsaDataSkeleton) {
            setStillAlive(false);
        } else {
            //set data here for data skeletons
            setSkeletonData();
        }

        p.fill(200,80);
        p.stroke(200, 80);

        //test
        p.drawBody2(joints);

        for (BodyPart bp : bodyParts) {
            bp.draw();
        }

        //If we're storing this skeleton's data for saving...
        if (storingDataNow) {
            storingDataNow();
        }

    }

    private void storingDataNow() {

        //each frame's data is 25 joints, each with 5 values. X, Y, Z and type / state.
        //Type and state are ints but store all as floats and convert on reload
        Float[] jointData = new Float[125];

        for (int i = 0; i < 25; i++) {

            jointData[i * 5] = joints[i].getX();
            jointData[(i * 5) + 1] = joints[i].getY();
            jointData[(i * 5) + 2] = joints[i].getZ();
            jointData[(i * 5) + 3] = (float) joints[i].getType();
            jointData[(i * 5) + 4] = (float) joints[i].getState();

        }

        //add to data array
        dataStore.add(jointData);

//            System.out.println("data storing... " + dataStore.size());
        if (GodsNMonsters.saveDataToFileOnNextFrame) {

            GodsNMonsters.saveDataToFileOnNextFrame = false;
            GodsNMonsters.saveDataFromSkeleton1 = false;
            storingDataNow = false;

            System.out.println("Writing data... ");

            StaticArrayReadWrite.writeArrayListOfFloatArrays(dataStore, "data/skeletonData/test1.csv");

        }

    }

    public void startStoringData() {

        storingDataNow = true;
        dataStore = new ArrayList<Float[]>();

    }

}

//CUTTINZ
//    public SkeletonWrapper(SkeletonFromData dskeleton) {
//        this.dskeleton = dskeleton;
//
//        //initialise joints
//        for (int i = 0; i < joints.length; i++) {
//            joints[i] = new Joint();
//        }
//        
//        //get data array
//
//        //get first instance of data and set joints
////        setSkeletonData(dskeleton);
//        
//        
//    }
