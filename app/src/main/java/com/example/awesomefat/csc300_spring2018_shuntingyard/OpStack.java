package com.example.awesomefat.csc300_spring2018_shuntingyard;

/**
 * Created by awesomefat on 2/27/18.
 */

public class OpStack
{
    private OpNode top;

    public OpStack()
    {
        this.top = null;
    }

    public OpNode peek()
    {
        return this.top;
    }

    public OpNode pop()
    {
        OpNode currTop = this.top;
        if(currTop != null)
        {
            this.top = (OpNode)currTop.getNextNode();
            currTop.setNextNode(null);
        }
        return currTop;
    }

    public void push(OpNode node)
    {
        //this is the logic for whether you can add a OpNode to the stack
        if(this.top == null)
        {
            this.top = node;
        }
        else
        {
            node.setNextNode(this.top);
            this.top = node;
        }
    }

    public boolean isEmpty() {
        return this.top == null;
    }
}
