package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import java.net.URI;

public interface StackoverflowClient {

    StackoverflowResponse fetchUpdate(Long id);

    StackoverflowResponse checkForUpdates(URI url);
}
