package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;

/**
 * {@link OptionHandler} for the option terminator "--".
 *
 * <p>
 * This {@link OptionHandler} can be used to implement the special token
 * "--" that indicates that the rest of tokens are not options, but arguments.
 *
 * <p>
 * For example, if you have the following class:
 *
 * <pre>
 * class Foo {
 *   &#64;Argument
 *   &#64;Option(name="--",handler={@link StopOptionHandler}.class)
 *   List&lt;String> args;
 *
 *   &#64;Option(name="-n")
 *   int n;
 * }
 * </pre>
 *
 * <p>
 * The command line {@code -n 5 abc def} would parse into n=5, args={"abc",def"},
 * but {@code -- -n 5 abc def} would parse into n=0, args={"-n","5","abc","def"}.
 *
 * @author Kohsuke Kawaguchi
 */
public class StopOptionHandler extends OptionHandler<String> {
    public StopOptionHandler(CmdLineParser parser, Option option, Setter<? super String> setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        int len = params.getParameterCount();
        for( int i=0; i<len; i++ ) {
            setter.addValue(params.getParameter(i));
        }
        return len;
    }

    public String getDefaultMetaVariable() {
        return "ARGUMENTS";
    }
}