package pureslick

import org.jbox2d.dynamics._
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.newdawn.slick.{Color, Image}

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/21/12
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */

trait Humanoid extends GameObject {
  var health: Int
  var displayDamaged = 0

  def die = {
    isGarbage = true
  }
  override def update() = {
    if(displayDamaged > 0) displayDamaged -= 1
  }

  def damaged(amount: Int) = {
    displayDamaged = 3
    health -= amount
    if (health < 0) die
  }

  def createBody(world: World): Body = {
    createBody(world, new Vec2(0, 0))
  }

  def createBody(world: World, pos: Vec2) = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = BodyType.DYNAMIC
    bodyDef.position = pos

    val bodyShape = new PolygonShape()
    bodyShape.setAsBox(40 / 20, 60 / 20)
    val bodyFixture = new FixtureDef()
    bodyFixture.shape = bodyShape
    bodyFixture.filter.categoryBits = 0x0002
    bodyFixture.filter.maskBits = 0x0004
    bodyFixture.filter.groupIndex = -1
    bodyFixture.userData = this

    val footShape = new PolygonShape()
    footShape.setAsBox(40 / 20, 20 / 20, new Vec2(0, 40 / 20), 0f)
    val footFixture = new FixtureDef()
    footFixture.shape = footShape
    footFixture.filter.categoryBits = 0x0001
    footFixture.filter.maskBits = 0x0001
    footFixture.filter.groupIndex = 1
    footFixture.userData = this

    val body = world.createBody(bodyDef)
    body.createFixture(bodyFixture)
    body.createFixture(footFixture)
    body.setUserData(this)
    body
  }

  override def render() = {
    val color = if (displayDamaged > 0) {new Color(1f,0,0,1f)} else {new Color(1f,1f,1f,1f)}
    image.draw((body.getPosition.x - image.getWidth/20)*10, (body.getPosition.y - image.getHeight/20)*10,color)
  }

}
