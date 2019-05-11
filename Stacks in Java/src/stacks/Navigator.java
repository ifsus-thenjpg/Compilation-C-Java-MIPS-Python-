package stacks;

import java.util.EmptyStackException;

/**
 *  Navigator Object can Set the current link via setCurrentLink(linkName) method, replace the current link
 * 	 by going back one link via goBack() method and replace the current link by
 * 	 going forward one link via goForward() method.
 */
public class Navigator {

    private String currentLink;
    private StackList<String> backLinks;
    private StackList<String> forwardLinks;

    /**
     * constructor that initializes the member attributes
     */
    public Navigator() {
        backLinks = new StackList<>("Back");
        forwardLinks = new StackList<>("Forward");
        currentLink = null;
    }

    /**
     * accessor method
     * @return currentLink
     */
    public String getCurrentLink() {
        return currentLink;
    }

    /**
     * accessor method
     * @return backLinks
     */
    public StackList<String> getBackLinks() {
        return backLinks;
    }

    /**
     * accessor method
     * @return getForwardLinks
     */
    public StackList<String> getForwardLinks() {
        return forwardLinks;
    }

    /**
     * mutator method
     * @param linkName assigns link passed as parameter to the current link , does not add empty objects to stack
     */
    public void setCurrentLink(String linkName) {
        if(!(currentLink == null)){ //handle pushing empty objects to the stack
            backLinks.push(currentLink);
        }
        this.currentLink = linkName;
        forwardLinks.clear();
    }

    /**
     * updates the currentLink to the link at the top of the backLinks stack
     */
    public void goBack() {
            try {
                if(backLinks.isEmpty()) throw new EmptyStackException(); //boundary case, handling locally
                forwardLinks.push(currentLink);
                currentLink = backLinks.pop(); //pop() should not throw an exception at this point given line 66
            } catch (EmptyStackException e) {
                System.out.println("\n[WARNING: Option has been disabled. You have no more <Back> links available]");
            }
        }
    /**
     * updates the currentLink to the link at the top of the forwardLinks stack
     */
    public void goForward() {
           try{ //if this try/catch wasn't handled in this method, the class calling goForward() would have to catch it
               if(forwardLinks.isEmpty())throw new EmptyStackException();
               backLinks.push(currentLink);
               currentLink = forwardLinks.pop();
           }catch (EmptyStackException e){
               System.out.println("\n[WARNING: Option has been disabled. You have no more <Forward> links available]");
           }
        }
}
