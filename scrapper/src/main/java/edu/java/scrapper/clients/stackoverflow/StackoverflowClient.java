package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.dto.stackoverflow.ListAnswer;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;

public interface StackoverflowClient {

    StackoverflowResponse fetchUpdate(Long id);

    ListAnswer checkForAnswers(Long id);
}
