package gitlet;

import java.io.IOException;

import static gitlet.Utils.*;


/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        if (args.length == 0) {
            throw error("Please enter a command.");
        }
        String firstArg = args[0];
        Repository repo = new Repository();
        switch(firstArg) {
            case "init":
                repo.init();
                break;
            case "add":
                repo.add(args[1]);
                break;
            case "commit":
                repo.commit(args[1]);
                break;
            case "rm":
                repo.rm(args[1]);
                break;
            case "log":
                break;
            case "global-log":
                break;
            case "find":
                break;
            case "status":
                break;
            case "checkout":
                int len = args.length;
                if (len == 3) {
                    repo.checkoutFromHead(args[2]);
                } else if (len == 4) {
                    repo.checkoutFromCommit(args[1], args[3]);

                } else if (len == 2) {
                    repo.checkoutFromBranch(args[1]);
                    
                }

                break;
            case "branch":
                break;
            case "rm-branch":
                break;
            case "reset":
                break;
            case "merge":
                break;
            default:
                throw error("No command with that name exists.");
        }
    }

}
