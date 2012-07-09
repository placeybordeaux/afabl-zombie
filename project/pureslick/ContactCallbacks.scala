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

class ContactCallbacks extends ContactListener {
  def beginContact(p1: Contact) {
  }

  def endContact(p1: Contact) {}

  def handleNodes(nodeA: ContactEdge, nodeB: ContactEdge) = (nodeA.other.getUserData, nodeB.other.getUserData) match {
    case (bullet: Bullet, humanoid: Humanoid) =>
      bullet.collide()
      humanoid.damaged(10)
    case (bullet: Bullet, _) =>
      bullet.collide()
    case (zombie: Zombie, human: Human) =>
      human.damaged(10)
    case (clip: Clip, human: Human) =>
      if (!clip.isGarbage){
      human.ammo += clip.ammo
      clip.isGarbage = true
  }

    case _ =>
  }

  def preSolve(p1: Contact, p2: Manifold) {
    handleNodes(p1.m_nodeA, p1.m_nodeB)
    handleNodes(p1.m_nodeB, p1.m_nodeA)

  }

  def postSolve(p1: Contact, p2: ContactImpulse) {}

}
