package clavardage.controller.connectivity;

import clavardage.model.objects.Conversation;

/**
 * Interface for conversation handling
 * @author Romain MONIER
 */
public interface ConversationActivity {
    /**
     * Override this method to set the action to do when a conversation is opened
     * @author Romain MONIER
     * @param r Runnable TCP Thread instance
     * @param c A Conversation DTO (can be null if retrieved later from database)
     */
    void conversationHandler(RunnableTCPThread r, Conversation c);
}
