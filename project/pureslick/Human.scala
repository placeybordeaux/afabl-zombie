package pureslick

import org.newdawn.slick.Image
import org.jbox2d.dynamics.World
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


  def fireBullet(mouseX: Int, mouseY: Int) = {
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

  override def render() = {
    super.render()
    bullets.foreach(_.render())
  }


  override def update() = {
    if (cooldown > 0) cooldown -= 1
    for (bullet <- bullets if bullet.collided)
      world.destroyBody(bullet.body)
    bullets = bullets.filter(!_.collided)
  }
}
