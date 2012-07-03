package pureslick

import org.jbox2d.callbacks.{ContactImpulse, ContactListener}
import org.jbox2d.dynamics.contacts.{ContactEdge, Contact}
import org.jbox2d.collision.Manifold

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/24/12
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */

class BulletContactCallback extends ContactListener {
  def beginContact(p1: Contact) {
  }

  def endContact(p1: Contact) {}

  def handleNodes(nodeA: ContactEdge, nodeB: ContactEdge) = {

    if (nodeA.other.getUserData.isInstanceOf[Bullet]) {
      val bullet = nodeA.other.getUserData.asInstanceOf[Bullet]
      bullet.collide()
    }
    if (nodeB.other.getUserData.isInstanceOf[Humanoid] && nodeA.other.getUserData.isInstanceOf[Bullet]) {
      val humanoid = nodeB.other.getUserData.asInstanceOf[Humanoid]
      humanoid.damaged(10)
    }

    if (nodeB.other.getUserData.isInstanceOf[Human] && nodeA.other.getUserData.isInstanceOf[Zombie]) {
      val humanoid = nodeB.other.getUserData.asInstanceOf[Human]
      humanoid.damaged(10)
    }

  }

  def handleNodes2(nodeA: ContactEdge, nodeB: ContactEdge) = (nodeA.other.getUserData, nodeB.other.getUserData) match {
    case (bullet: Bullet, humanoid: Humanoid) =>
      bullet.collide()
      humanoid.damaged(10)
    case (bullet: Bullet, _) =>
      bullet.collide()
    case (zombie: Zombie, human: Human) =>
      human.damaged(10)
    case _ =>
  }

  def preSolve(p1: Contact, p2: Manifold) {
    handleNodes2(p1.m_nodeA, p1.m_nodeB)
    handleNodes2(p1.m_nodeB, p1.m_nodeA)

  }

  def postSolve(p1: Contact, p2: ContactImpulse) {}

}
