package com.company.other;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import sun.misc.Unsafe;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by dongweizhao on 16/7/22.
 */
public class MyTest {
static int i;
    @Test
    public void test1(){
        for (int i = 0; i <10 ; i++) {
            try {
                if (i == 5) {
                    System.out.println(1/0);
                }
                System.out.println("i="+i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }
    @Test
    public void test2(){
        int count=20;
        System.out.println(Integer.toBinaryString(count));
        count=count<<0;
        System.out.println("count:"+count);
        //System.out.println("二进制："+Integer.toBinaryString(count));
        count=count>>>1;
        System.out.println("count:"+count);
        System.out.println("二进制："+Integer.toBinaryString(count));
    }
    @Test
    public void test3(){
        synchronized (this){

        }
        char c=11122;
        String str="11";
    }
   public void test(String str){
       System.out.println("str = [" + str + "]");
   }
    public static void main(String[] args) {
        System.out.println(i);
        int str[]=new int[5];
        System.out.println("args = [" + str[2] + "]");

        int i=1;
        switch(i) {
            case 0: System.out.println("zero"); break;
            case 1: System.out.println("one");
            case 2: System.out.println("two");
            default: System.out.println("default");
        }
        String s="11212dddd";
        s=s+1;
        System.out.printf(s);
    }
    @Test
    public void testCas(){
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
    }



    @Test
    public void testNode(){
        Node head=new Node("head");
        Node tail=head;
        Node t=tail;
        Node node=new Node("node");
        node.prev=t;
        tail=node;
        t.next=node;
        System.out.println("11");
    }
    @Test
    public void testIF(){
        if (!tacc()&&tacc2()){
            System.out.println("判断");
        }
    }
    public boolean tacc(){
        System.out.println("进入acc");
        return true;
    }
    public boolean tacc2(){
        System.out.println("进入acc2");
        return false;
    }

    @Test
    public void test10() throws NoSuchPaddingException, NoSuchAlgorithmException {
        //BouncyCastleProvider bouncyCastleProvider=new BouncyCastleProvider();
        for (;;){
            Cipher cipher = Cipher.getInstance("RSA",new BouncyCastleProvider()
                    );
        }

    }

    static final class Node{

        public  String name = "";
        /** Marker to indicate a node is waiting in shared mode */
        static final Node SHARED = new Node();
        /** Marker to indicate a node is waiting in exclusive mode */
        static final Node EXCLUSIVE = null;

        /** waitStatus value to indicate thread has cancelled */
        static final int CANCELLED =  1;
        /** waitStatus value to indicate successor's thread needs unparking */
        static final int SIGNAL    = -1;
        /** waitStatus value to indicate thread is waiting on condition */
        static final int CONDITION = -2;
        /**
         * waitStatus value to indicate the next acquireShared should
         * unconditionally propagate
         */
        static final int PROPAGATE = -3;

        /**
         * Status field, taking on only the values:
         *   SIGNAL:     The successor of this node is (or will soon be)
         *               blocked (via park), so the current node must
         *               unpark its successor when it releases or
         *               cancels. To avoid races, acquire methods must
         *               first indicate they need a signal,
         *               then retry the atomic acquire, and then,
         *               on failure, block.
         *   CANCELLED:  This node is cancelled due to timeout or interrupt.
         *               Nodes never leave this state. In particular,
         *               a thread with cancelled node never again blocks.
         *   CONDITION:  This node is currently on a condition queue.
         *               It will not be used as a sync queue node
         *               until transferred, at which time the status
         *               will be set to 0. (Use of this value here has
         *               nothing to do with the other uses of the
         *               field, but simplifies mechanics.)
         *   PROPAGATE:  A releaseShared should be propagated to other
         *               nodes. This is set (for head node only) in
         *               doReleaseShared to ensure propagation
         *               continues, even if other operations have
         *               since intervened.
         *   0:          None of the above
         *
         * The values are arranged numerically to simplify use.
         * Non-negative values mean that a node doesn't need to
         * signal. So, most code doesn't need to check for particular
         * values, just for sign.
         *
         * The field is initialized to 0 for normal sync nodes, and
         * CONDITION for condition nodes.  It is modified using CAS
         * (or when possible, unconditional volatile writes).
         */
        volatile int waitStatus;

        /**
         * Link to predecessor node that current node/thread relies on
         * for checking waitStatus. Assigned during enqueing, and nulled
         * out (for sake of GC) only upon dequeuing.  Also, upon
         * cancellation of a predecessor, we short-circuit while
         * finding a non-cancelled one, which will always exist
         * because the head node is never cancelled: A node becomes
         * head only as a result of successful acquire. A
         * cancelled thread never succeeds in acquiring, and a thread only
         * cancels itself, not any other node.
         */
        volatile Node prev;

        /**
         * Link to the successor node that the current node/thread
         * unparks upon release. Assigned during enqueuing, adjusted
         * when bypassing cancelled predecessors, and nulled out (for
         * sake of GC) when dequeued.  The enq operation does not
         * assign next field of a predecessor until after attachment,
         * so seeing a null next field does not necessarily mean that
         * node is at end of queue. However, if a next field appears
         * to be null, we can scan prev's from the tail to
         * double-check.  The next field of cancelled nodes is set to
         * point to the node itself instead of null, to make life
         * easier for isOnSyncQueue.
         */
        volatile Node next;

        /**
         * The thread that enqueued this node.  Initialized on
         * construction and nulled out after use.
         */
        volatile Thread thread;

        /**
         * Link to next node waiting on condition, or the special
         * value SHARED.  Because condition queues are accessed only
         * when holding in exclusive mode, we just need a simple
         * linked queue to hold nodes while they are waiting on
         * conditions. They are then transferred to the queue to
         * re-acquire. And because conditions can only be exclusive,
         * we save a field by using special value to indicate shared
         * mode.
         */
        Node nextWaiter;

        /**
         * Returns true if node is waiting in shared mode
         */
        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        /**
         * Returns previous node, or throws NullPointerException if null.
         * Use when predecessor cannot be null.  The null check could
         * be elided, but is present to help the VM.
         *
         * @return the predecessor of this node
         */
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        public Node(String name) {
            this.name = name;
        }

        Node() {    // Used to establish initial head or SHARED marker
        }


        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }
}
