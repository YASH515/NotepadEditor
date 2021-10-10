
package notepadeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;                  // when we will implement NotepadEditor to ActionListener it will show some
import javax.swing.UIManager;                  // error , to remove that we have to implement all the abstract method .
                                               
public class NotepadEditor implements ActionListener,WindowListener 
        {  
        JMenuItem neww ,open,saveas,save,cut,copy,paste,font,font_color,background_color ;  
        JTextArea textarea ;                     
        File file;
        JFrame jf,font_frame ;  // when we will click on font , a new frame will appear
        JComboBox font_family,font_size,font_style;
        JButton ok;
        // these variables declared as instance variables
        
        NotepadEditor(){             //constructor
        
        try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // to improve the look
        }
        catch(Exception e)
        {    
        }    
            
        jf = new JFrame("*Untitled*-Notepad");
        jf.setSize(500,400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        jf.setLocationRelativeTo(null);
        JMenuBar jmenubar = new JMenuBar();   
        JMenu file = new JMenu("File");
        jmenubar.add(file);
        neww = new JMenuItem("New");
        neww.addActionListener(this);  
        file.add(neww);
        open = new JMenuItem("Open");   // add menu item before setting menu bar on frame 
        open.addActionListener(this);
        file.add(open);
        save = new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
        saveas = new JMenuItem("Save As ..");
        saveas.addActionListener(this);
        file.add(saveas);
        JMenu edit = new JMenu("Edit");
        jmenubar.add(edit);
        cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        edit.add(cut);
        copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        edit.add(copy);
        paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        edit.add(paste);
        
        JMenu format = new JMenu("Format");   // menu by the name of Format
        
        font = new JMenuItem("Font");
        font.addActionListener(this);        
        format.add(font);
        
        font_color = new JMenuItem("Font color");
        font_color.addActionListener(this);
        format.add(font_color);
        
        background_color = new JMenuItem("Background color");
        background_color.addActionListener(this);
        format.add(background_color);
        jmenubar.add(format);
        
        jf.setJMenuBar(jmenubar);
        textarea = new JTextArea();  // add text area on our frame
        JScrollPane scrollpane = new JScrollPane(textarea);    // adding scroll bar 
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  // ver and horizontal scrollbar
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);     // will  always be there
    
        jf.add(scrollpane);
        jf.addWindowListener(this);
        jf.setVisible(true);
        }

    public static void main(String[] args) {
        new NotepadEditor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==neww)
        {
            newFile();
        }
        if(e.getSource()==open)
        {
           openFile();
        }
        if(e.getSource() ==save)
        {
           saveFile(); 
        }
        if(e.getSource()==saveas)
        {
            saveAsFile();
        }
        if(e.getSource() ==cut)
        {
            textarea.cut();
        }
        if(e.getSource() ==copy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste)
        {
            textarea.paste();
        }
        if(e.getSource()==font)
        {
            openFontFrame();
        }
        if(e.getSource()==ok)
        {
            setFontOnTextArea();
        }
        if(e.getSource()==font_color)
        {
           Color c = JColorChooser.showDialog(jf, "Choose Font Color", Color.black);
           textarea.setForeground(c);   // foreground is for text
        }
        if(e.getSource()==background_color)
        {
            Color c = JColorChooser.showDialog(jf,"Choose Background Color",Color.white);
            textarea.setBackground(c);  
        }
    }   
        void setFontOnTextArea()
        {
            String fontfamily = (String)font_family.getSelectedItem();    // getSelectedItem returns object file 
            String fontsize = (String)font_size.getSelectedItem();
            String fontstyle = (String)font_style.getSelectedItem();   // getting selected values from font box
            // Font is a predefined class
            int style=0;
            if(fontstyle.equals("plain"))
            {
                style=0;
            }
            else if(fontstyle.equals("bold"))
            {
                style=1;
            }
            else if(fontstyle.equals("italic"));
            {
                style=2;
            }
            Font fontt = new Font(fontfamily,style,Integer.parseInt(fontsize));  // plain , bold , italic , these cannot be 
            textarea.setFont(fontt);                                          // converted into integers 
            
            font_frame.setVisible(false);
        }
        void openFontFrame()
        {
            font_frame = new JFrame("Choose Font");
            font_frame.setSize(500,500);    
            font_frame.setLocationRelativeTo(jf);
            font_frame.setLayout(null);     // when set layout will be null then only set bounds will work properly 
            
            String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            font_family = new JComboBox(fonts);            // for calling all fonts in jframe
            font_family.setBounds(50,100,100,30); // x,y,w,h            //search get all fonts in jframe
            font_frame.add(font_family);
            
            String []sizes={"10","12","14","18","24","28","34","42","72"};
            font_size = new JComboBox(sizes);            
            font_size.setBounds(170,100,100,30); // x,y,w,h            
            font_frame.add(font_size);
            
            String []styles={"plain","bold","italic"};   // we use arrays for storing values    
            font_style = new JComboBox(styles);
            font_style.setBounds(300,100,100,30); // x,y,w,h            
            font_frame.add(font_style);
            
            ok=new JButton("OK");
            ok.setBounds(180,200,100,50);
            ok.addActionListener(this);     // without this , ok button will not perform any action
            font_frame.add(ok);
            
            font_frame.setVisible(true);
        }
        
        void saveFile()
        {
            String title = jf.getTitle();
            if(title.equals("*Untitled*-Notepad")) // this means its a new file
            {
                saveAsFile();
            }
            else
            {
                String text = textarea.getText();                       // we have not created file object again because file 
                try(FileOutputStream fos = new FileOutputStream(file))  // has already been made . here the changes made in that file 
                {                                                       // will be saved .
                      byte []b = text.getBytes();  // file is saved in form of an array 
                      fos.write(b);
                }
                catch(IOException ee)
                {
                    ee.printStackTrace();
                }
            }
        }
        void openFile()
        {
            JFileChooser fileChooser = new JFileChooser();  // for opening files from any folder
            int result = fileChooser.showOpenDialog(jf);
            if(result==0) //when the value of result is zero ,it means we have selected a file and clicked on open
            {              
            textarea.setText("");
            file = fileChooser.getSelectedFile();
            jf.setTitle(file.getName());
            try(FileInputStream fis = new FileInputStream(file)) // this try catch block is used to open file
            {                                                 // try with resource does not require finally block to 
                int i;                                        // close the file which was opened
                while( (i=fis.read()) != -1)             // -1 means there is no more text left
                {
               //     System.out.print((char)i);  : this will give output in the below dialog box , not in our frame
                    textarea.append(String.valueOf((char)i));
                }
            }
            catch(IOException ee)
            {
                ee.printStackTrace();
            }
        }
        }
        void saveAsFile()
        {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(jf);
            if(result==0)
            {
                String text = textarea.getText();
                file = fileChooser.getSelectedFile();
                jf.setTitle(file.getName());
                try(FileOutputStream fos = new FileOutputStream(file))
                {
                      byte []b = text.getBytes();  // file is saved in form of an array 
                      fos.write(b);
                }
                catch(IOException ee)
                {
                    ee.printStackTrace();
                }
                       
            }
        }
        void newFile()
        {
           String text = textarea.getText();
           if(!text.equals(""))
           {
               int i = JOptionPane.showConfirmDialog(jf, "Do you want to save this file ? ");
               if(i==0)  // 0 means yes
               {
                  saveFile();
                  textarea.setText("");
                  jf.setTitle("*Untitled*-Notepad");
               }
               else if(i==1)  // 1 means no 
               {
                   textarea.setText("");
               }
           }
        }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("111111");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        JOptionPane.showConfirmDialog(jf, "Do you want to save this file ?");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("33333");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("44444");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("555555");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("6666666");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("777777");
    }  

    }

    
    

