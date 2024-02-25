package edu.java.clients.stackoverflow;

import edu.java.dto.stackoverflow.StackoverflowResponse;

public interface StackoverflowClient {

    StackoverflowResponse fetchUpdate(Long id);
}
