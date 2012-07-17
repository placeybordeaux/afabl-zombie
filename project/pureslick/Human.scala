package pureslick

import org.newdawn.slick.Image
import org.jbox2d.dynamics.{Fixture, World}
import org.jbox2d.common.Vec2
import collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/30/12
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */

trait Human extends Humanoid {
  var health = 100
  var ammo = 0
  var cooldown = 0
  var bullets = ListBuffer[Bullet]()
  val world: World
  var speed = 20

  override def damaged(amount: Int) = {
    super.damaged(amount)
    image = new Image("data/player" + (1 + (health*4)/100).toString + ".png")
  }

  def fireBullet(vec: Vec2): Unit = {
    fireBullet(vec.x,vec.y)
  }

  def trace_path(vec: Vec2): GameObject = {
    def getPositionVec(): Vec2 = {
      var posx, posy = 0f
      if (math.abs(vec.x) >= math.abs(vec.y)){
        posx = body.getPosition.x + (1 + image.getWidth / 40)*(vec.x/math.abs(vec.x))
        posy = body.getPosition.y + 3
      }
      else {
        posy = body.getPosition.y + (1 + image.getHeight / 40)*(vec.y/math.abs(vec.y))
        posx = body.getPosition.x
      }
      new Vec2(posx, posy)
    }
    val callback = new tracingCallback()
    world.raycast(callback,getPositionVec,vec)
    println(callback.gameObject)
    callback.gameObject
  }

  def fireBullet(mouseX: Float, mouseY: Float) = {
    if (!(mouseY == mouseX && mouseY == 0)){
    def getPositionVec(): Vec2 = {
      var posx, posy = 0f
      if (math.abs(mouseX) >= math.abs(mouseY)){
        posx = body.getPosition.x + (1 + image.getWidth / 40)*(mouseX/math.abs(mouseX))
        posy = body.getPosition.y + 3
      }
      else {
        posy = body.getPosition.y + (1 + image.getHeight / 40)*(mouseY/math.abs(mouseY))
        posx = body.getPosition.x
      }
      new Vec2(posx, posy)
    }
    if (cooldown == 0 && ammo > 0){
      val pos = getPositionVec
      val direction = new Vec2(mouseX - pos.x, mouseY - pos.y)
      direction.normalize
      bullets.append(new Bullet(world, pos, direction.mul(600000)))
      cooldown = 30
      ammo -= 1
    }
  }
  }

  override def render() = {
    super.render()
    bullets.foreach(_.render())
  }


  override def update() = {
    super.update()
    if (cooldown > 0) cooldown -= 1
    for (bullet <- bullets if bullet.collided)
      world.destroyBody(bullet.body)
    bullets = bullets.filter(!_.collided)
  }
}
