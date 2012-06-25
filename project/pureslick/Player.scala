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

class Player(img: Image, world: World) extends Humanoid {
  val image = img
  val body = createBody(world)
  var health = 100
  var bullets = ListBuffer[Bullet]()

  def handleInput(input: Input) = {
    var x, y = 0
    if (input.isKeyDown(Input.KEY_DOWN))
      y += 90
    if (input.isKeyDown(Input.KEY_UP))
      y -= 90
    if (input.isKeyDown(Input.KEY_LEFT))
      x -= 90
    if (input.isKeyDown(Input.KEY_RIGHT))
      x += 90
    if (input.isMouseButtonDown(0)) {
      fireBullet(input)
    }
    walk(x, y)
  }

  def fireBullet(input: Input) = {
    def getPositionVec(): Vec2 = {
      var posx, posy = 0f
      if (input.getMouseX >= body.getPosition.x + image.getWidth / 2)
        posx = (body.getPosition.x + image.getWidth / 2) + 6
      else if (input.getMouseX <= body.getPosition.x - 26)
        posx = (body.getPosition.x) - 26
      else
        posx = input.getMouseX
      if (input.getMouseY >= body.getPosition.y + image.getHeight / 2)
        posy = (body.getPosition.y + image.getHeight / 2) + 6
      else if (input.getMouseY <= body.getPosition.y - 36)
        posy = body.getPosition.y - 36
      else
        posy = input.getMouseY
      new Vec2(posx, posy)
    }

    val pos = getPositionVec
    val direction = new Vec2(input.getMouseX - pos.x, input.getMouseY - pos.y)
    direction.normalize
    println("mouse (" + input.getMouseX + "," + input.getMouseY + ")")
    println("body" + body.getPosition)
    println("dir" + direction)
    println("pos" + pos)
    bullets.append(new Bullet(world, pos, direction.mul(600000)))

  }

  def walk(x: Float, y: Float) {
    body.setLinearVelocity(new Vec2(x, y))
  }

  def update() = {
    for (bullet <- bullets if bullet.collided)
      world.destroyBody(bullet.body)
    bullets = bullets.filter(!_.collided)
  }

  override def render() = {
    super.render()
    bullets.foreach(_.render())
  }

}
