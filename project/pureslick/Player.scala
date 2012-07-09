package pureslick

import org.newdawn.slick.{Input, Image}
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2
import collection.mutable.ListBuffer


/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/13/12
 * Time: 2:18 AM
 * To change this template use File | Settings | File Templates.
 */

class Player(img: Image, b2World: World) extends Human {
  var image = img
  val world = b2World
  val body = createBody(world)
  ammo = 10

  def handleInput(input: Input) = {
    var x, y = 0
    if (input.isKeyDown(Input.KEY_S))
      y += 9
    if (input.isKeyDown(Input.KEY_W))
      y -= 9
    if (input.isKeyDown(Input.KEY_A))
      x -= 9
    if (input.isKeyDown(Input.KEY_D))
      x += 9
    if (input.isMouseButtonDown(0)) {
      fireBullet(input.getMouseX - 400,input.getMouseY - 300)
    }
    walk(x, y)
  }

  def walk(x: Float, y: Float) {
    body.setLinearVelocity(new Vec2(x, y))
  }


}
