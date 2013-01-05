package pureslick

import org.newdawn.slick.Image
import org.jbox2d.dynamics.{Fixture, World}
import org.jbox2d.common.Vec2
import collection.mutable.ListBuffer
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/30/12
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */

trait Human extends Humanoid {
  var health = 130
  var ammo = 2
  var cooldown = 0
  var bullets = ListBuffer[Bullet]()
  val world: World
  var speed = (20 + (Random.nextFloat - .5)*3).toFloat

  override def damaged(amount: Int) = {
    super.damaged(amount)
    image = new Image("data/player" + (1 + ((math.max(health,0)*4)/130)).toString + ".png")
  }

  def fireBullet(vec: Vec2): Unit = {
    fireBullet(vec.x,vec.y)
  }

  def getPositionVec(vec: Vec2): Vec2 = {
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


  def Hits_zombie(vec: Vec2): Boolean = {
    val callback = new tracingCallback()
    world.raycast(callback,getPositionVec(vec),vec.add(new Vec2(0,5)))
    if(!callback.gameObject.isInstanceOf[Zombie]) return false

    world.raycast(callback,getPositionVec(vec),vec.add(new Vec2(0,-5)))
    if(!callback.gameObject.isInstanceOf[Zombie]) return false

    world.raycast(callback,getPositionVec(vec),vec.add(new Vec2(5,0)))
    if(!callback.gameObject.isInstanceOf[Zombie]) return false

    world.raycast(callback,getPositionVec(vec),vec.add(new Vec2(-5,0)))
    if(!callback.gameObject.isInstanceOf[Zombie]) return false
    return true
  }

  def fireBullet(mouseX: Float, mouseY: Float) = {
    if (!(mouseY == mouseX && mouseY == 0)){
    if (cooldown == 0 && ammo > 0){
      val pos = getPositionVec(new Vec2(mouseX,mouseY))
      val direction = new Vec2(mouseX - pos.x, mouseY - pos.y)
      direction.normalize
      bullets.append(new Bullet(world, pos, direction.mul(600000)))
      cooldown = 20
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
