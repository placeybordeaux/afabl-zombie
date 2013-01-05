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
  ammo = 50
  speed = 24

  def handleInput(input: Input) = {
    var x, y = 0f
    if (input.isKeyDown(Input.KEY_S))
      y += speed
    if (input.isKeyDown(Input.KEY_W))
      y -= speed
    if (input.isKeyDown(Input.KEY_A))
      x -= speed
    if (input.isKeyDown(Input.KEY_D))
      x += speed
    if (input.isMouseButtonDown(0)) {
      fireBullet(-5 + input.getMouseX - 400 + body.getPosition.x,-30 + input.getMouseY - 300 + body.getPosition.y)
    }
    walk(x, y)
  }

  def walk(x: Float, y: Float) {
    body.setLinearVelocity(new Vec2(x, y))
  }


}
