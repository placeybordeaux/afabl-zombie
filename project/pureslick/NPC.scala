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
      var zombieNearby = false
      var fireDirection: Vec2 = null
      //this is for random wandering behavior when they are out of range of a human

      var direction = body.getLinearVelocity
      direction.normalize
      direction = direction.add(new Vec2(Random.nextFloat / 2 - .25f, Random.nextFloat / 2 - .25f))
      //end wandering behavior

      for (humanoid <- observation.humanoids) {
        if (humanoid.isInstanceOf[Zombie]) {
          val newDirection = body.getPosition.sub(humanoid.body.getPosition.add(new Vec2(image.getWidth/20,image.getHeight/20)))
          if (newDirection.length() < closest && newDirection.length() < farthest) {
            closest = newDirection.length
            fireDirection = newDirection
            direction = direction.add(newDirection)
            zombieNearby = true
          }
        }
      }
    if (zombieNearby && trace_path(direction.mul(-1.0f)).isInstanceOf[Zombie] && cooldown > 0 && ammo > 0){
      println("fired!")
      fireBullet((fireDirection.mul(-3.0f)))
    }



    direction.normalize()

      body.setLinearVelocity(direction.mul(speed))
      //fireBullet(direction.negate())
  }
}
