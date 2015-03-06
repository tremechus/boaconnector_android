package com.sqlboaconnector.server;

import com.sqlboaconnector.server.command.ExecCommand;
import com.sqlboaconnector.server.command.GreetingsCommand;
import com.sqlboaconnector.server.command.QueryForStringListCommand;
import com.sqlboaconnector.server.command.RawExecCommand;

import java.util.HashMap;

/**
 * Created by trevor on 3/5/2015.
 */
public class CommandMap extends HashMap<String, ServerCommand> {

    public CommandMap() {
        put("greetings", new GreetingsCommand());
        put("queryForStringList", new QueryForStringListCommand());
        put("exec", new ExecCommand());
        put("rawExec", new RawExecCommand());

    }


}
