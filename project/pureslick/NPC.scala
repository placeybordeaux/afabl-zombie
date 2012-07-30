package pureslick

import org.newdawn.slick.Image
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/30/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */

class NPC(b2World: World, x: Float, y: Float) extends Human {
  var image = new Image("data/player.png")
  val world = b2World
  val body = createBody(world, new Vec2(x, y))

  override def update(observation: Observation) = {
      super.update()
      var closest = 9999f
      val farthest = 30f
      var nearestZombie: Zombie = null
      var nearestClip: Clip = null
      var nearestHuman: Human = null

      //this is for random wandering behavior when they are out of range of a human
      var direction = body.getLinearVelocity
      direction.normalize
      direction = direction.add(new Vec2(Random.nextFloat / 2 - .25f, Random.nextFloat / 2 - .25f))
      //end wandering behavior
      for (o <- observation.objects) {
        o match {
          case z: Zombie =>
            if (z == null) nearestZombie = z
              if (this.distanceTo(z) < this.distanceTo(nearestZombie))
                nearestZombie = z
          case c: Clip =>
            if (nearestClip == null) nearestClip = c
            if (this.distanceTo(c) < this.distanceTo(nearestClip))
              nearestClip = c
          case h: Human =>
            if (h != this){
            if (nearestHuman == null) nearestHuman = h
            if (this.distanceTo(h) < this.distanceTo(nearestHuman))
              nearestHuman = h
            }

          case _ =>
        }
      }

    //figure out direction to go in
    if (this.distanceTo(nearestZombie) < 20){
      val d = body.getPosition.sub(nearestZombie.body.getPosition)
      direction = direction.add(d.mul(.3f/(d.length() + 0.1f)))
    }else if(distanceTo(nearestClip)< 10){
      val d = nearestClip.body.getPosition.sub(body.getPosition)
      direction = direction.add(d mul .2f/(d.length() + 0.1f))
    }else if (distanceTo(nearestHuman) < 90){
      val d = body.getPosition.sub(nearestHuman.body.getPosition)
      direction = direction.add(d mul .1f/(d.length() + 0.1f))
    }

    //fire bullet at the nearest zombie
    if (nearestZombie != null  && cooldown == 0 && ammo > 0 && Hits_zombie(nearestZombie.body.getPosition)){
      fireBullet(nearestZombie.body.getPosition)
    }

    direction.normalize()

      body.setLinearVelocity(direction.mul(speed))
      //fireBullet(direction.negate())
  }
}
