package pureslick

import org.newdawn.slick.Image
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/22/12
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */

class Zombie(world: World, initalX: Float, initalY: Float) extends Humanoid {
  val image = new Image("data/zombie.png")
  val body = createBody(world, new Vec2(initalX, initalY))
  var health = 200
  val speed = 30
  var x = initalX
  var y = initalY

  def this(world: World) = this(world, 100, 100)

  override def update(observation: Observation) = {
    x = body.getPosition.x
    y = body.getPosition.y
    for (humanoid <- observation.Humanoids) {
      if (humanoid.isInstanceOf[Player]) {
        var direction = (humanoid.body.getPosition sub body.getPosition)
        direction.normalize()
        direction = direction.mul(speed)
        println(direction)
        body.setLinearVelocity(direction)
      }
    }
  }

}
