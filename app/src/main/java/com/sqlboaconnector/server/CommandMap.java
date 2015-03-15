package com.sqlboaconnector.server;

import com.sqlboaconnector.server.command.QueryCommand;
import com.sqlboaconnector.server.command.HandshakeCommand;
import com.sqlboaconnector.server.command.ListCommand;
import com.sqlboaconnector.server.command.RawExecCommand;

import java.util.HashMap;

/**
 * Created by trevor on 3/5/2015.
 */
public class CommandMap extends HashMap<String, ServerCommand> {

    public CommandMap() {
        put("handshake", new HandshakeCommand());
        put("list", new ListCommand());
        put("query", new QueryCommand());
        put("exec", new RawExecCommand());

    }


}
