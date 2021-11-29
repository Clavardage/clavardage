package clavardage.controller.connectivity;

import clavardage.model.objects.Conversation;

public interface ConversationActivity {
    void conversationHandler(RunnableTCPThread r, Conversation c);
}
