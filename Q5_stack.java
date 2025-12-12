//5. STACK - Google Docs Undo/Redo Manager 
//Problem: 
//Design the undo/redo engine for Google Docs supporting text editing. 
//Requirements: 
// Handle 10,000+ operations. 
// Undo stack can store only latest 50 actions (drop oldest automatically). 
// Any new action clears the redo stack. 
// Support two actions: 
//o INSERT(x) 
//o DELETE(k characters) 
// Return: 
//o final document 
//o total operations performed 
package assessment;
import java.util.Stack;

class Op{
    char type;
    String data;
    Op(char t,String d){type=t;data=d;}
}

public class stack{
    StringBuilder doc=new StringBuilder();
    Stack<Op> undo=new Stack<>();
    Stack<Op> redo=new Stack<>();
    int undoLimit=50;
    int totalOps=0;

    public void insert(String text){
        totalOps++;
        doc.append(text);
        pushUndo(new Op('I',text));
        redo.clear();
    }

    public void delete(int k){
        totalOps++;
        if(k<=0)return;
        if(k>doc.length())k=doc.length();
        String removed=doc.substring(doc.length()-k);
        doc.delete(doc.length()-k,doc.length());
        pushUndo(new Op('D',removed));
        redo.clear();
    }

    public boolean undo(){
        if(undo.empty())return false;
        totalOps++;
        Op op=undo.pop();
        if(op.type=='I'){
            doc.delete(doc.length()-op.data.length(),doc.length());
        }else{
            doc.append(op.data);
        }
        redo.push(op);	
        return true;
    }
    public boolean redo(){
        if(redo.empty())return false;
        totalOps++;
        Op op=redo.pop();
        if(op.type=='I'){
            doc.append(op.data);
        }else{
            doc.delete(doc.length()-op.data.length(),doc.length());
        }
        undo.push(op);
        return true;
    }
    private void pushUndo(Op op){
        if(undo.size()==undoLimit)undo.remove(0);
        undo.push(op);
    }
    public String getDocument(){return doc.toString();}
    public int getTotalOps(){return totalOps;}
    public static void main(String[] args){
        stack e = new stack();
        e.insert("Hello");
        e.insert(" World");
        e.delete(6);
        e.undo();
        e.undo();
        e.redo();
        e.insert("!");
        System.out.println("Final Document: " + e.getDocument());
        System.out.println("Total Operations: " + e.getTotalOps());
    }
    
}
