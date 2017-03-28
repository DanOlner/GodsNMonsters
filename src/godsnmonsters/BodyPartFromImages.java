/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package godsnmonsters;

import java.util.ArrayList;

/**
 *
 * @author Dan Olner
 */
public class BodyPartFromImages implements BodyPart {

    GodsNMonsters p;
    Joint[] joints;
    
    ArrayList<BodyPiece> pieces = new ArrayList<>();

    public BodyPartFromImages(GodsNMonsters p, Joint[] joints) {
        this.p = p;
        this.joints = joints;
    }
    
    public void newBodyPiece(BodyPiece bp){
        
        pieces.add(bp);
        
    }
    
    @Override
    public void draw(){
        
        for(BodyPiece bp : pieces){
            bp.draw();
        }
        
    }
    
    
}
