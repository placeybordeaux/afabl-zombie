package pureslick

import org.newdawn.slick.Image
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/22/12
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */

class Zombie(world: World, x: Float, y: Float) extends Humanoid {
  var image = new Image("data/zombie.png")
  val body = createBody(world, new Vec2(x, y))
  var health = 200
  val speed = 3

  def this(world: World) = {
    this(world,100,100)
  }

  def this(world: World, vect: Vec2) = {
    this(world,vect.x,vect.y)
  }

  override def update(observation: Observation) = {
    //this is for random wandering behavior when they are out of range of a human
    var direction = body.getLinearVelocity
    direction.normalize
    direction = direction.add(new Vec2(Random.nextFloat/2 - .25f,Random.nextFloat/2 - .25f))
    //end wandering behavior
    var closest = 9999f
    val farthest = 20
    for (humanoid <- observation.humanoids){
      if (humanoid.isInstanceOf[Human]){
        val newDirection = humanoid.body.getPosition.sub(body.getPosition)
        if (newDirection.length() < closest && newDirection.length() < farthest){
          direction = newDirection
          closest = direction.length
        }
      }
    }
    direction.normalize()
    body.setLinearVelocity(direction.mul(speed))
  }
}
