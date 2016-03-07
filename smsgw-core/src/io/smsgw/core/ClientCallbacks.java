package io.smsgw.core;

import io.smsgw.core.request.CommandRequest;

public interface ClientCallbacks {

    void onCommand(CommandRequest commandRequest);

}
