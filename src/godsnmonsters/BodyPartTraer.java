/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package godsnmonsters;

import traer.physics.*;

/**
 *
 * @author Dan Olner
 */
public class BodyPartTraer implements BodyPart {

    ParticleSystem physics;
    Particle p;
    Particle anchor;
    Spring s;
    
    

    public BodyPartTraer(GodsNMonsters p, Joint[] joints) {
    }

    @Override
    public void draw() {
        
//        System.out.println("this would be drawing physics");
        
    }
    
    

}
