/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package godsnmonsters;

import KinectPV2.KJoint;

/**
 * Can't extend KJoint so will need to wrap data from it when it's coming from Kinect.
 *
 * @author Dan Olner
 */
public class Joint {

    //vars from KJoint to wrap
    private float x, y, z;
    private int type, state;

    public void setJoints(float x, float y, float z, int type, int state) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.state = state;
    }

    /**
     * Get joint data from KJoint object via Kinect
     *
     * @param j
     */
    public void setJoints(KJoint j) {
        this.x = j.getX();
        this.y = j.getY();
        this.z = j.getZ();
        this.type = j.getType();
        this.state = j.getState();
        
//        System.out.println("set Joints. Type: " + this.type + ", state: " + this.state);
        
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
