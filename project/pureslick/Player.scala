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
  var cooldown = 0

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
    val mouseX = input.getMouseX - 400 + body.getPosition.x
    val mouseY = input.getMouseY - 300 + body.getPosition.y
    def getPositionVec(): Vec2 = {
      var posx, posy = 0f
      if (mouseX >= body.getPosition.x + image.getWidth / 2)
        posx = (body.getPosition.x + image.getWidth / 2) + 6
      else if (mouseX <= body.getPosition.x - 26)
        posx = (body.getPosition.x) - 26
      else
        posx = mouseX
      if (mouseY >= body.getPosition.y + image.getHeight / 2)
        posy = (body.getPosition.y + image.getHeight / 2) + 6
      else if (mouseY <= body.getPosition.y - 36)
        posy = body.getPosition.y - 36
      else
        posy = mouseY
      new Vec2(posx, posy)
    }
    if (cooldown == 0){
    val pos = getPositionVec
    val direction = new Vec2(mouseX - pos.x, mouseY - pos.y)
    direction.normalize
    println("mouse (" + input.getMouseX + "," + input.getMouseY + ")")
    println("body" + body.getPosition)
    println("dir" + direction)
    println("pos" + pos)
    bullets.append(new Bullet(world, pos, direction.mul(600000)))
    cooldown = 0
    }

  }

  def walk(x: Float, y: Float) {
    body.setLinearVelocity(new Vec2(x, y))
  }

  override def update() = {
    if (cooldown > 0) cooldown -= 1
    for (bullet <- bullets if bullet.collided)
      world.destroyBody(bullet.body)
    bullets = bullets.filter(!_.collided)
  }

  override def render() = {
    super.render()
    bullets.foreach(_.render())
  }

}
