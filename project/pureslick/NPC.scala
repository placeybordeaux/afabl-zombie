package pureslick

import org.newdawn.slick.Image
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/30/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */

class NPC(world: World,x: Float,y: Float) extends Human{
  val image = new Image("data/player.png")
  val body = createBody(world, new Vec2(x,y))
  val speed = 40

  override def update(observation: Observation) = {
    var closest = 9999f
    for (humanoid <- observation.humanoids){
      if (humanoid.isInstanceOf[Zombie]){
        val direction = body.getPosition.sub(humanoid.body.getPosition)
        if (direction.length() < closest){
          closest = direction.length
          direction.normalize()
          body.setLinearVelocity(direction.mul(speed))
        }
      }
    }
  }
}
