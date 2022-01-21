package clavardage.view.listener;

import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.objects.User;
import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageWindow;

public class ActionConnectivity {
	
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
	
	public static void reorganiseListByConnectivity(User userUpdated, boolean connect) throws Exception {
		MessageWindow w = Application.getMessageWindow();

		w.getListUsers().removeAll();
		boolean isNew = true ;
		for (DestinataireJPanel user : w.getAllUsers()) {
			if (user.getIdDestinataire().equals(userUpdated.getUUID())) {
				isNew = false;
			}
		} 
		if (isNew && !userUpdated.getUUID().equals(AuthOperations.getConnectedUser().getUUID())) {
			w.addNewUserToList(userUpdated, connect);
		}
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
