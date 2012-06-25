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

class Zombie(world: World) extends Humanoid {
  val image = new Image("data/zombie.png")
  val body = createBody(world, new Vec2(100, 100))
  var health = 200

  override def update(observation: Observation) = {
  }
}
