package clavardage.view.listener;

import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.objects.User;
import clavardage.view.Application;
import clavardage.view.MessageWindow;
import clavardage.view.alert.popup.ActionLogUpdated;
import clavardage.view.main.DestinataireJPanel;

/**
 * Gathers the methods modifying the connectivity of the users.
 * 
 * @see #setConnected(UUID, Boolean)
 * @see #reorganizeListByConnectivity(User, boolean)
 * 
 * @author Célestine Paillé
 */
public class ActionConnectivity {
	
	/**
	 * Set the connectivity of the user with the UUID <code>idUser</code> based on <code>connect</code>. <p>
	 * 
	 * Find the user based on his UUID and if he isn't mark with the blue pellet, 
	 * set his connectivity based on <code>connect</code>.
	 * 
	 * @param idUser the UUID of the user we want to update his connectivity
	 * @param connect the new connectivity of the user
	 * 
	 * @see DestinataireJPanel#setConnected(boolean)
	 */
	public static void setConnected(UUID idUser, Boolean connect) {
		MessageWindow w = Application.getMessageWindow();

		for (DestinataireJPanel u : w.getAllUsers()) {
			if (u.getIdDestinataire().equals(idUser)) {
				if (!u.isOpen()) {
					u.setConnected(connect) ;
					u.revalidate();
				}
			}
		}
		w.getUsersContainer().revalidate();
	}
	
	/**
	 * Reorganize the users list by connectivity.<p>
	 * 
	 * First of all, check if <code>userUpdated</code> is known.<br>
	 * If <code>userUpdated</code> is new and he is not the current user, add him to the list.<br>
	 * If <code>userUpdated</code> is known, check is login. If the login has changed, a popup appears to warn the user and the change is save.<p>
	 * 
	 * Then, displays the list with the open conversations first, then the connected users and then the rest of the users.
	 * 
	 * @param userUpdated the user who was updated
	 * @param connect the connectivity of userUpdated
	 * 
	 * @throws Exception if the user of the app is not connected or if <code>addNewUserToList()</code> has failed
	 * 
	 * @see ActionLogUpdated
	 * @see MessageWindow#addNewUserToList(User, Boolean)
	 */
	public static void reorganizeListByConnectivity(User userUpdated, boolean connect) throws Exception {
		MessageWindow w = Application.getMessageWindow();
		if (userUpdated != null) {
			
			//check if the user is known
			boolean isNew = true ;
			for (DestinataireJPanel user : w.getAllUsers()) {
				if (user.getIdDestinataire().equals(userUpdated.getUUID())) {
					isNew = false;
					
					//check if the login is new
					if (!userUpdated.getLogin().equals(user.getNameDestinataire())) {	
						new ActionLogUpdated(user.getNameDestinataire(), userUpdated.getLogin());
						user.setNameDestinataire(userUpdated.getLogin());
						if (w.getDiscussionDisplay().getIdConversation().equals(user.getIdDestinataire())) {
							w.setNameDestinataire(userUpdated.getLogin());
						}
					}
				}
			}
			if (isNew && !userUpdated.getUUID().equals(AuthOperations.getConnectedUser().getUUID())) {
				w.addNewUserToList(userUpdated, connect);
			}
			
			//display the reorganize list
			w.getListUsers().removeAll();

			for (DestinataireJPanel user : w.getAllUsers()) {
				if (user.isOpen()) {
					w.getListUsers().add(user);
				}
			}
			for (DestinataireJPanel user : w.getAllUsers()) {
				if (!user.isOpen() && user.isConnected()) {
					w.getListUsers().add(user);
				}
			}
			for (DestinataireJPanel user : w.getAllUsers()) {
				if (!user.isOpen() && !user.isConnected()) {
					w.getListUsers().add(user);
				}
			}	
		}
	}

}
