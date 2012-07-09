package pureslick

import org.jbox2d.common.Vec2
import org.newdawn.slick.Image
import org.jbox2d.dynamics._
import org.jbox2d.collision.shapes.PolygonShape

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 7/8/12
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */

class Clip(world: World, pos: Vec2, amount: Int) extends GameObject {
  var image = new Image("data/clip.png")
  val body = createBody(world,pos)
  val ammo = amount

  def this(world: World, x: Float, y: Float, amount: Int) = {
    this(world, new Vec2(x,y), amount)
  }

  def this(world: World, x: Float, y: Float) = {
    this(world, new Vec2(x,y), 10)
  }

  def this(world: World, pos: Vec2) = {
    this(world, pos, 10)
  }

  def createBody(world: World, pos: Vec2): Body = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = BodyType.DYNAMIC
    bodyDef.position = pos
    bodyDef.bullet = true

    val bodyShape = new PolygonShape()
    bodyShape.setAsBox(image.getWidth / 20f, image.getHeight / 20f)
    val bodyFixture = new FixtureDef()
    bodyFixture.shape = bodyShape
    bodyFixture.density = 0.00005f
    bodyFixture.filter.categoryBits = 0x0004
    bodyFixture.filter.maskBits = 0x0007
    bodyFixture.filter.groupIndex = -2

    val body = world.createBody(bodyDef)
    body.createFixture(bodyFixture)
    body.setUserData(this)
    body

  }

}
