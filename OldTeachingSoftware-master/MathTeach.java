import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;
import java.text.*;
import java.util.*;
import java.io.*;

public class MathTeach extends Applet implements ItemListener, ActionListener
{
    //Constants
    static int buttonSize = 50;
    static int screenWidth = 800;
    static int screenHeight = 600;

    //Mass Button creation
    private static Button ButtonHelp;
    private static Button ButtonCredits;
    private static Button ButtonLesson;
    private static Button ButtonChallenge;
    private static Button ButtonMenu;
    private static Button QUIT;
    private Button ButtonNext;
    private Button ButtonPrevious;
    private Button ButtonEasy;
    private Button ButtonMedium;
    private Button ButtonHard;
    private Button ButtonStats;
    private Button ButtonLeaderboard;
    private Button PLAY;
    private Button A;
    private Button B;
    private Button C;
    private Button D;
    private Button E;

    TextField name = new TextField (2);

    //Regular Variables for use later
    boolean showStats = false;
    boolean showLeaderboard = false;
    boolean gameRunning = false;
    static int temp1;
    static int pageNumber = 1;
    static int currentScreen = 1;
    static int difficulty = 1;
    static int time = 0;
    static String selectedAnswer = "";
    static String correctAnswer = "";
    static int currentScore = 0;
    static int currentStreak = 0;
    static int cycle = 0;
    static int questionMode = 1; //0 = unanswered, 1 = good, 2 = bad
    static int randomPosition;
    static int fakeNum1 = 0, fakeNum2 = 0, fakeNum3 = 0, fakeNum4 = 0;
    static int num1, num2, num3;
    //num1 ^ num2 = num3

    //Temporary library of in game stats
    double[] stats = new double [10];

    //Permanent library of top players
    static String[] topScorePlayer = new String [15];
    static double[] topScoreScore = new double [15];

    //Stock images
    Image img1;
    Image img2;
    Image img3;
    Image img4;
    Image img5;

    public void init ()
    {
	//AudioClip sound1 = getAudioClip (getDocumentBase (), "sound1.wav");*********************************************************************

	//Reads leaderboard file list.
	try
	{
	    topScores ();
	}
	catch (Exception e)
	{
	}

	//Load images
	img1 = getImage (getDocumentBase (), "background1.jpg");
	img2 = getImage (getDocumentBase (), "icon.png");
	img3 = getImage (getDocumentBase (), "superman.png");
	//img4 = getImage (getDocumentBase (), "image4.jpg");
	//img5 = getImage (getDocumentBase (), "image5.jpg");

	this.add (this.name);

	//Button creation
	this.ButtonMenu = new Button ("Menu");
	this.add (this.ButtonMenu);
	ButtonMenu.addActionListener (this);
	this.ButtonHelp = new Button ("Help");
	this.add (this.ButtonHelp);
	ButtonHelp.addActionListener (this);
	this.ButtonCredits = new Button ("Credits");
	this.add (this.ButtonCredits);
	ButtonCredits.addActionListener (this);
	this.ButtonLesson = new Button ("Lesson");
	this.add (this.ButtonLesson);
	ButtonLesson.addActionListener (this);
	this.ButtonChallenge = new Button ("Challenge");
	this.add (this.ButtonChallenge);
	ButtonChallenge.addActionListener (this);
	this.ButtonNext = new Button ("Next");
	this.add (this.ButtonNext);
	ButtonNext.addActionListener (this);
	this.ButtonPrevious = new Button ("Previous");
	this.add (this.ButtonPrevious);
	ButtonPrevious.addActionListener (this);
	this.ButtonEasy = new Button ("Easy");
	this.add (this.ButtonEasy);
	ButtonEasy.addActionListener (this);
	this.ButtonMedium = new Button ("Medium");
	this.add (this.ButtonMedium);
	ButtonMedium.addActionListener (this);
	this.ButtonHard = new Button ("Hard");
	this.add (this.ButtonHard);
	ButtonHard.addActionListener (this);
	this.ButtonStats = new Button ("Stats");
	this.add (this.ButtonStats);
	ButtonStats.addActionListener (this);
	this.ButtonLeaderboard = new Button ("Top Scores");
	this.add (this.ButtonLeaderboard);
	ButtonLeaderboard.addActionListener (this);
	this.QUIT = new Button ("QUIT");
	this.add (this.QUIT);
	QUIT.addActionListener (this);
	this.A = new Button ("A");
	this.add (this.A);
	A.addActionListener (this);
	this.B = new Button ("B");
	this.add (this.B);
	B.addActionListener (this);
	this.C = new Button ("C");
	this.add (this.C);
	C.addActionListener (this);
	this.D = new Button ("D");
	this.add (this.D);
	D.addActionListener (this);
	this.E = new Button ("E");
	this.add (this.E);
	E.addActionListener (this);
	this.PLAY = new Button ("PLAY");
	this.add (this.PLAY);
	PLAY.addActionListener (this);

	//Start with some buttons unusable
	ButtonNext.setEnabled (false);
	ButtonPrevious.setEnabled (false);
	ButtonEasy.setEnabled (false);
	ButtonEasy.setVisible (false);
	ButtonMedium.setEnabled (false);
	ButtonMedium.setVisible (false);
	ButtonHard.setEnabled (false);
	ButtonHard.setVisible (false);
	ButtonStats.setEnabled (false);
	ButtonStats.setVisible (false);
	ButtonLeaderboard.setEnabled (false);
	ButtonLeaderboard.setVisible (false);
	QUIT.setEnabled (false);
	QUIT.setVisible (false);
	PLAY.setEnabled (false);
	PLAY.setVisible (false);
    }


    public void itemStateChanged (ItemEvent ie)
    { //For use when nessesary
    }


    public void paint (Graphics g)
    {
	//Set size and location of all buttons, whether or not currently in use
	this.ButtonHelp.setLocation (20, 300);
	this.ButtonHelp.setSize (2 * buttonSize, buttonSize);
	this.ButtonCredits.setLocation (20, 375);
	this.ButtonCredits.setSize (2 * buttonSize, buttonSize);
	this.ButtonLesson.setLocation (20, 450);
	this.ButtonLesson.setSize (2 * buttonSize, buttonSize);
	this.ButtonChallenge.setLocation (20, 525);
	this.ButtonChallenge.setSize (2 * buttonSize, buttonSize);
	this.ButtonMenu.setLocation (20, 175);
	this.ButtonMenu.setSize (2 * buttonSize, (int) (1.5 * buttonSize));
	this.ButtonNext.setLocation (700, 550);
	this.ButtonNext.setSize (buttonSize + 20, buttonSize / 2);
	this.ButtonPrevious.setLocation (600, 550);
	this.ButtonPrevious.setSize (buttonSize + 20, buttonSize / 2);
	this.ButtonEasy.setLocation (300, 500);
	this.ButtonEasy.setSize (buttonSize + 20, buttonSize / 2);
	this.ButtonMedium.setLocation (400, 500);
	this.ButtonMedium.setSize (buttonSize + 20, buttonSize / 2);
	this.ButtonHard.setLocation (500, 500);
	this.ButtonHard.setSize (buttonSize + 20, buttonSize / 2);
	this.ButtonStats.setLocation (600, 500);
	this.ButtonStats.setSize (buttonSize + 20, buttonSize / 2);
	this.ButtonLeaderboard.setLocation (700, 500);
	this.ButtonLeaderboard.setSize (buttonSize + 20, buttonSize / 2);
	this.PLAY.setLocation (700, 390);
	this.PLAY.setSize (70, 80);
	this.QUIT.setLocation (700, 390);
	this.QUIT.setSize (70, 80);
	this.A.setLocation (200, 400);
	this.A.setSize (80, 100);
	this.B.setLocation (300, 400);
	this.B.setSize (80, 100);
	this.C.setLocation (400, 400);
	this.C.setSize (80, 100);
	this.D.setLocation (500, 400);
	this.D.setSize (80, 100);
	this.E.setLocation (600, 400);
	this.E.setSize (80, 100);
	this.name.setLocation (650, 20);
	this.name.setSize (120, 25);

	//Enable certain buttons only if they're in the correct screen
	if (currentScreen != 4)
	{
	    ButtonNext.setEnabled (false);
	    ButtonPrevious.setEnabled (false);
	}
	if (currentScreen != 5)
	{
	    ButtonEasy.setEnabled (false);
	    ButtonEasy.setVisible (false);
	    ButtonMedium.setEnabled (false);
	    ButtonMedium.setVisible (false);
	    ButtonHard.setEnabled (false);
	    ButtonHard.setVisible (false);
	    ButtonStats.setEnabled (false);
	    ButtonStats.setVisible (false);
	    ButtonLeaderboard.setEnabled (false);
	    ButtonLeaderboard.setVisible (false);
	    PLAY.setEnabled (false);
	    PLAY.setVisible (false);
	}
	if (currentScreen != 6)
	{
	    //If in challenge IDLE page, reset.
	    time = 0;
	    currentScore = 0;
	    QUIT.setEnabled (false);
	    QUIT.setVisible (false);
	    A.setEnabled (false);
	    A.setVisible (false);
	    B.setEnabled (false);
	    B.setVisible (false);
	    C.setEnabled (false);
	    C.setVisible (false);
	    D.setEnabled (false);
	    D.setVisible (false);
	    E.setEnabled (false);
	    E.setVisible (false);
	}

	//Insert decoration
	g.drawImage (img1, 0, 0, this);
	g.drawImage (img2, 20, 30, this);
	g.drawOval (20, 30, 104, 104);
	g.fillOval (110, 120, 20, 20);
	g.fillOval (130, 110, 10, 10);

	g.setFont (new Font ("TimesRoman", Font.PLAIN, 13));
	g.setColor (Color.BLACK);
	g.drawString ("NAME:", 600, 38);

	if (currentScreen == 1) ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	{ //Menu

	    g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
	    g.drawString ("MATHMAN CHALLENGE", 250, 150);
	    g.drawLine (230, 165, 750, 165);
	    g.setFont (new Font ("TimesRoman", Font.PLAIN, 20));
	    g.drawString ("Version 1.0.0", 550, 195);

	    int[] tempX1 = {420, 460, 460};
	    int[] tempY1 = {300, 250, 290};

	    g.drawImage (img3, 225, 275, this);
	    g.setColor (Color.WHITE);
	    g.fillPolygon (tempX1, tempY1, 3);
	    g.setColor (Color.BLACK);
	    g.drawPolygon (tempX1, tempY1, 3);
	    g.setColor (Color.WHITE);
	    g.fillOval (435, 215, 200, 100);
	    g.setColor (Color.BLACK);
	    g.drawOval (435, 215, 200, 100);
	    g.drawString ("MATH IS FOR", 475, 260);
	    g.drawString ("THE MIGHTY", 475, 280);
	}

	if (currentScreen == 2) ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	{ //Help

	    g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
	    g.drawString ("Help", 450, 150);
	    g.drawLine (230, 165, 750, 165);
	    g.setFont (new Font ("TimesRoman", Font.PLAIN, 14));

	    String[] text1 = new String [10];
	    text1 [0] = "    What will I learn?";
	    text1 [1] = "This math program allows you to learn the basics of radicals, and exponents.";
	    text1 [2] = "We will also teach you to estimate effectively, as it will be especially important";
	    text1 [3] = "when dealing with these weird, irrational numbers.";
	    text1 [4] = "    What else can I do?";
	    text1 [5] = "You can test your skills in our 'Challenge' mode, where you will compete against the";
	    text1 [6] = "time, and the scores of other's to try and take the high scores. Further instructions";
	    text1 [7] = "are provided in the 'Challenge' section. It also provides temporary stats!";
	    text1 [8] = "If you wish to check out all the credits that is due, goto 'credits'.";
	    text1 [9] = "Date of program creation: 2016-12-13 - 2016-1-5";

	    g.drawString (text1 [0], 250, 250);
	    g.drawString (text1 [1], 250, 270);
	    g.drawString (text1 [2], 250, 290);
	    g.drawString (text1 [3], 250, 310);
	    g.drawString (text1 [4], 250, 350);
	    g.drawString (text1 [5], 250, 370);
	    g.drawString (text1 [6], 250, 390);
	    g.drawString (text1 [7], 250, 410);
	    g.drawString (text1 [8], 250, 470);
	    g.drawString (text1 [9], 250, 520);
	}


	if (currentScreen == 3) ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	{ //Credits
	    g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
	    g.drawString ("Credits", 450, 150);
	    g.drawLine (230, 165, 750, 165);
	    g.setFont (new Font ("TimesRoman", Font.PLAIN, 14));

	    String[] text2 = new String [10];
	    text2 [0] = "       Author:";
	    text2 [1] = "Timothy Cao [The creator of all]";
	    text2 [2] = "       Assistance:";
	    text2 [3] = "stackoverflow.com. | Paint.net | oracale.com";
	    text2 [4] = "       Links:";
	    text2 [5] = "http://www.istockphoto.com/  --  Backgrounds | drawception.com ";

	    g.drawString (text2 [0], 250, 250);
	    g.drawString (text2 [1], 250, 270);
	    g.drawString (text2 [2], 250, 310);
	    g.drawString (text2 [3], 250, 330);
	    g.drawString (text2 [4], 250, 370);
	    g.drawString (text2 [5], 250, 390);
	}


	if (currentScreen == 4) ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	{ //Lesson

	    if (ButtonNext.isEnabled () == false)
	    {
		ButtonNext.setEnabled (true);
		ButtonPrevious.setEnabled (true);
		repaint ();
	    }
	    else
	    {
		g.setColor (Color.white);
		g.drawString ("Page " + pageNumber + " / 4", 700, 530);
	    }

	    g.setColor (Color.black);

	    if (pageNumber == 1) //*************************************************************************************************
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("LESSON 1: ", 250, 150);
		g.drawLine (230, 165, 750, 165);

		g.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		g.drawString ("Estimation", 550, 195);

		String[] text3 = new String [15];
		text3 [0] = "Many may confuse Estimation with guessing. Though they have similarities, they are not the same.";
		text3 [1] = "Estimation requires some mental calculation, but instead of actually finding the answer, very rounded";
		text3 [2] = "numbers are used. Sometimes, it can also be benificial to use previously known answers to approximate";
		text3 [3] = "";
		text3 [4] = "The amount of calculations required will be based on the significant figures expected. (Long for s.f.)";
		text3 [5] = "Significant figures means how many digits are known. This means it excludes all non decimal zeros.";
		text3 [6] = "";
		text3 [7] = "EXAMPLE:   Estimate 7 x 8 x 9 x 10 x 11 x 12 to 1 sigificant figures.";
		text3 [8] = "   Answer: We see that the numbers are all roughly averaged around 10, and there are 6 numbers.";
		text3 [9] = " We can estimate that the answer will be close to 10 x 10 x 10 x 10 x 10 x 10 or 1 million.";
		text3 [10] = "We can also figure out the last digit. 7 x 8 = 56, keep the last digit -- 6. 6*9 = 54, remove the last";
		text3 [11] = "digit, etc. you end up with 0 as the last digit. This can help you alot. Also count how many times ";
		text3 [12] = "You taken away other digits, as that will help you decide how many digits there are total.";
		text3 [13] = "";
		text3 [14] = "Estimation is a useful tool to help confirm your answers.";

		g.drawString (text3 [0], 200, 250);
		g.drawString (text3 [1], 200, 270);
		g.drawString (text3 [2], 200, 290);
		g.drawString (text3 [3], 200, 310);
		g.drawString (text3 [4], 200, 330);
		g.drawString (text3 [5], 200, 350);
		g.drawString (text3 [6], 200, 370);
		g.drawString (text3 [7], 200, 390);
		g.drawString (text3 [8], 200, 410);
		g.drawString (text3 [9], 200, 430);
		g.drawString (text3 [10], 200, 450);
		g.drawString (text3 [11], 200, 470);
		g.drawString (text3 [12], 200, 490);
		g.drawString (text3 [13], 200, 510);
		g.drawString (text3 [14], 200, 530);
	    }
	    else if (pageNumber == 2) //*************************************************************************************************
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("LESSON 2: ", 250, 150);
		g.drawLine (230, 165, 750, 165);
		g.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		g.drawString ("Exponents", 550, 195);

		String[] text4 = new String [15];
		text4 [0] = "Squaring numbers is just what it sounds like. To make a square out of a number. ";
		text4 [1] = "It's basically multiplying the number byself (Just like finding the area from a side length)";
		text4 [2] = "Squaring a number is denoated by a subscript two, or the symbol ' ^2 '.";
		text4 [3] = "";
		text4 [4] = "EXAMPLE: What is 10^2 ?  (What is 10 squared?) (or also known as 10 to the power of 2)";
		text4 [5] = "   Answer: 10^2 is the same as 10 x 10. This equals 100.";
		text4 [6] = "";
		text4 [7] = "There also exists cubing a number. And just like you'd expect, instead of multiplying a number";
		text4 [8] = "by itself once, you do it again. So 10^3 would mean 10 x 10 x 10. This keeps on going of course";
		text4 [9] = "10^10 would be 10 x 10 x 10 x 10 x 10 x 10 x 10 x 10 x 10 x 10. or 10 Billion";
		text4 [10] = "";
		text4 [11] = "The number after the ' ^ ' AKA the number on the subscript, is call the ' exponent'.";
		text4 [12] = "The number before the ' ^ ' AKA the number that is being subscripted, is called the 'base'.";
		text4 [13] = "";
		text4 [14] = "To say 6^4 for example, you would say 6 to the power of 4. (6 x 6 x 6 x 6) or 1296";
		g.drawString (text4 [0], 200, 250);
		g.drawString (text4 [1], 200, 270);
		g.drawString (text4 [2], 200, 290);
		g.drawString (text4 [3], 200, 310);
		g.drawString (text4 [4], 200, 330);
		g.drawString (text4 [5], 200, 350);
		g.drawString (text4 [6], 200, 370);
		g.drawString (text4 [7], 200, 390);
		g.drawString (text4 [8], 200, 410);
		g.drawString (text4 [9], 200, 430);
		g.drawString (text4 [10], 200, 450);
		g.drawString (text4 [11], 200, 470);
		g.drawString (text4 [12], 200, 490);
		g.drawString (text4 [13], 200, 510);
		g.drawString (text4 [14], 200, 530);
	    }
	    else if (pageNumber == 3) //*************************************************************************************************
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("LESSON 3:", 250, 150);
		g.drawLine (230, 165, 750, 165);
		g.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		g.drawString ("Roots (Radicals)", 550, 195);

		String[] text5 = new String [15];
		text5 [0] = "Square root is the opposite of squaring. You are given the area of a square, and you must find";
		text5 [1] = "the number that needs to multiply itself by itself to equal that number. (The side length)";
		text5 [2] = "";
		text5 [3] = "EXAMPLE: What is the square root of 100?";
		text5 [4] = "   Answer: What number times itself = 100? or find x, when x^2 = 100. This is 10, because = 100.";
		text5 [5] = "";
		text5 [6] = "This ALSO extends past squares. You can find cube roots by looking for a number when multiplied";
		text5 [7] = "itself TWICE (three times total), that will equal the answer. (Side length of a cube of x volume)";
		text5 [8] = "";
		text5 [9] = "For example: The cuberoot of 27 is 3, because 3^3 = 27. You could also say 27^(1/3) = 3.";
		text5 [10] = "To express a root as an exponent, make it an exponent, find the reciprocal of the number exponent";
		text5 [11] = "";
		text5 [12] = "EXAMPLE: Write the square root of 30 in exponents";
		text5 [13] = "  Answer: 30^(1/2) because square roots means x^2 = 30. And the reciprocal of 2 is is 1/2";
		text5 [14] = "";
		g.drawString (text5 [0], 200, 250);
		g.drawString (text5 [1], 200, 270);
		g.drawString (text5 [2], 200, 290);
		g.drawString (text5 [3], 200, 310);
		g.drawString (text5 [4], 200, 330);
		g.drawString (text5 [5], 200, 350);
		g.drawString (text5 [6], 200, 370);
		g.drawString (text5 [7], 200, 390);
		g.drawString (text5 [8], 200, 410);
		g.drawString (text5 [9], 200, 430);
		g.drawString (text5 [10], 200, 450);
		g.drawString (text5 [11], 200, 470);
		g.drawString (text5 [12], 200, 490);
		g.drawString (text5 [13], 200, 510);
		g.drawString (text5 [14], 200, 530);
	    }
	    else if (pageNumber == 4) //*************************************************************************************************
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("LESSON OVERVIEW", 250, 150);
		g.drawLine (230, 165, 750, 165);
		g.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		g.drawString ("Combining the lessons", 550, 195);

		String[] text6 = new String [15];
		text6 [0] = "Review: ";
		text6 [1] = "";
		text6 [2] = "4^5 means 4 x 4 x 4 x 4 x 4. This is called 4 to the power of 5";
		text6 [3] = "";
		text6 [4] = "4^1/5 means the 5th root of 4. or x^5 = 4. What times itself 5 times equals 4";
		text6 [5] = "";
		text6 [6] = "Square root and cube roots have special names, but just mean x^(1/2) and x^(1/3)";
		text6 [7] = "respectively. They just happen to have more applications and are more used";
		text6 [8] = "";
		text6 [9] = "Estimation gets harder as the numbers get more intimidating. For example 4.5^7.";
		text6 [10] = "The answer may request it in scientific notation, e.g. 10^x. In this case, 4.5^7";
		text6 [11] = "is 37367 approximately, so the answer would be 10^4, because it has 5 digits, just";
		text6 [12] = "like 38367. (10^4 = 10,000 = 10 x 10 x 10 x 10 x 10)";
		text6 [13] = "";
		text6 [14] = "";
		g.drawString (text6 [0], 200, 250);
		g.drawString (text6 [1], 200, 270);
		g.drawString (text6 [2], 200, 290);
		g.drawString (text6 [3], 200, 310);
		g.drawString (text6 [4], 200, 330);
		g.drawString (text6 [5], 200, 350);
		g.drawString (text6 [6], 200, 370);
		g.drawString (text6 [7], 200, 390);
		g.drawString (text6 [8], 200, 410);
		g.drawString (text6 [9], 200, 430);
		g.drawString (text6 [10], 200, 450);
		g.drawString (text6 [11], 200, 470);
		g.drawString (text6 [12], 200, 490);
		g.drawString (text6 [13], 200, 510);
		g.drawString (text6 [14], 200, 530);
	    }

	}


	if (currentScreen == 5) ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	{ //Challenge

	    temp1 = 3;
	    if (ButtonEasy.isEnabled () == false)
	    {
		ButtonEasy.setEnabled (true);
		ButtonEasy.setVisible (true);
		ButtonMedium.setEnabled (true);
		ButtonMedium.setVisible (true);
		ButtonHard.setEnabled (true);
		ButtonHard.setVisible (true);
		ButtonStats.setEnabled (true);
		ButtonStats.setVisible (true);
		ButtonLeaderboard.setEnabled (true);
		ButtonLeaderboard.setVisible (true);
		PLAY.setEnabled (true);
		PLAY.setVisible (true);
		repaint ();
	    }
	    if (showStats == true)
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("Session stats", 250, 150);
		g.drawLine (230, 165, 750, 165);

		g.setFont (new Font ("TimesRoman", Font.PLAIN, 15));
		String[] text8 = new String [15];

		text8 [0] = "Session best: " + stats [0];
		text8 [1] = "Session worst: " + stats [1];
		text8 [2] = "";
		text8 [3] = "Best streak: " + stats [2];
		text8 [4] = "Longest wrong streak: " + stats [3];
		text8 [5] = "";
		text8 [6] = "Fastest 5-in-a-row answer: " + stats [4];
		text8 [7] = "Total seconds played: " + stats [5];
		text8 [8] = "Total games played: " + stats [6];
		text8 [9] = "Total minigames played: " + stats [7];
		text8 [10] = "Best minigame score: " + stats [8];
		text8 [11] = "";
		text8 [12] = "";
		text8 [13] = "";
		text8 [14] = "";
		g.drawString (text8 [0], 200, 250);
		g.drawString (text8 [1], 200, 270);
		g.drawString (text8 [2], 200, 290);
		g.drawString (text8 [3], 200, 310);
		g.drawString (text8 [4], 200, 330);
		g.drawString (text8 [5], 200, 350);
		g.drawString (text8 [6], 200, 370);
		g.drawString (text8 [7], 200, 390);
		g.drawString (text8 [8], 200, 410);
		g.drawString (text8 [9], 200, 430);
		g.drawString (text8 [10], 200, 450);
		g.drawString (text8 [11], 200, 470);
		g.drawString (text8 [12], 200, 490);
		g.drawString (text8 [13], 200, 510);
		g.drawString (text8 [14], 200, 530);
	    }
	    else if (showLeaderboard == true)
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("Leaderboards", 250, 150);
		g.drawLine (230, 165, 750, 165);

		String[] text9 = new String [15];
		text9 [0] = "1. " + topScorePlayer [0] + " : " + topScoreScore [0];
		text9 [1] = "2. " + topScorePlayer [1] + " : " + topScoreScore [1];
		text9 [2] = "3. " + topScorePlayer [2] + " : " + topScoreScore [2];
		text9 [3] = "4. " + topScorePlayer [3] + " : " + topScoreScore [3];
		text9 [4] = "5. " + topScorePlayer [4] + " : " + topScoreScore [4];
		text9 [5] = "6. " + topScorePlayer [5] + " : " + topScoreScore [5];
		text9 [6] = "7. " + topScorePlayer [6] + " : " + topScoreScore [6];
		text9 [7] = "8. " + topScorePlayer [7] + " : " + topScoreScore [7];
		text9 [8] = "9. " + topScorePlayer [8] + " : " + topScoreScore [8];
		text9 [9] = "10. " + topScorePlayer [9] + " : " + topScoreScore [9];
		text9 [10] = "11. " + topScorePlayer [10] + " : " + topScoreScore [10];
		text9 [11] = "12. " + topScorePlayer [11] + " : " + topScoreScore [11];
		text9 [12] = "13. " + topScorePlayer [12] + " : " + topScoreScore [12];
		text9 [13] = "14. " + topScorePlayer [13] + " : " + topScoreScore [13];
		text9 [14] = "15. " + topScorePlayer [14] + " : " + topScoreScore [14];
		g.setFont (new Font ("TimesRoman", Font.BOLD, 21));
		g.drawString (text9 [0], 270, 240);
		g.setFont (new Font ("TimesRoman", Font.BOLD, 18));
		g.drawString (text9 [1], 270, 265);
		g.setFont (new Font ("TimesRoman", Font.BOLD, 16));
		g.drawString (text9 [2], 270, 290);
		g.setFont (new Font ("TimesRoman", Font.BOLD, 12));
		g.drawString (text9 [3], 200, 330);
		g.drawString (text9 [4], 200, 350);
		g.drawString (text9 [5], 200, 370);
		g.drawString (text9 [6], 200, 390);
		g.drawString (text9 [7], 200, 410);
		g.drawString (text9 [8], 200, 430);
		g.drawString (text9 [9], 400, 330);
		g.drawString (text9 [10], 400, 350);
		g.drawString (text9 [11], 400, 370);
		g.drawString (text9 [12], 400, 390);
		g.drawString (text9 [13], 400, 410);
		g.drawString (text9 [14], 400, 430);
		//Show session highscore
		g.drawString ("Your session best: " + stats [0], 500, 250);
	    }
	    else
	    {
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));
		g.drawString ("CHALLENGE", 250, 150);
		g.drawLine (230, 165, 750, 165);
		g.setFont (new Font ("TimesRoman", Font.PLAIN, 16));

		if (difficulty == 1)
		    g.drawString ("Difficulty: easy (!)", 550, 195);
		else if (difficulty == 2)
		    g.drawString ("Difficulty: medium (!!)", 550, 195);
		else if (difficulty == 3)
		    g.drawString ("Difficulty: hard (!!!)", 550, 195);


		String[] text7 = new String [15];
		text7 [0] = "Instructions:";
		text7 [1] = "";
		text7 [2] = "You start with 60 seconds";
		text7 [3] = "Answer the questions as fast as you can";
		text7 [4] = "Getting it correct will give you points";
		text7 [5] = "Equal the speed you complete the question";
		text7 [6] = "";
		text7 [7] = "But guessing isn't good, because you lose";
		text7 [8] = "points. Harder difficulty will punish more.";
		text7 [9] = "";
		text7 [10] = "Selecting harder difficulty is more rewarding";
		text7 [11] = "though when you get streaks. You can earn more.";
		text7 [12] = "";
		text7 [13] = "Try and make the leaderboards! Check your stats too!";
		text7 [14] = "";
		g.drawString (text7 [0], 200, 250);
		g.drawString (text7 [1], 200, 270);
		g.drawString (text7 [2], 200, 290);
		g.drawString (text7 [3], 200, 310);
		g.drawString (text7 [4], 200, 330);
		g.drawString (text7 [5], 200, 350);
		g.drawString (text7 [6], 200, 370);
		g.drawString (text7 [7], 200, 390);
		g.drawString (text7 [8], 200, 410);
		g.drawString (text7 [9], 200, 430);
		g.drawString (text7 [10], 200, 450);
		g.drawString (text7 [11], 200, 470);
		g.drawString (text7 [12], 200, 490);
		g.setColor (Color.white);
		g.drawString (text7 [13], 200, 550);
		g.drawString (text7 [14], 200, 530);
		g.setColor (Color.black);
	    }

	}


	if (currentScreen == 6) ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	{ //Game

	    if (gameRunning == false)
	    {
		turnOff ();
		g.setFont (new Font ("TimesRoman", Font.BOLD, 36));

		for (int i = 3 ; i > 0 ; i--)
		{
		    g.setColor (Color.white);
		    g.fillRect (220, 110, 400, 50);
		    g.setColor (Color.black);
		    g.drawRect (220, 110, 400, 50);

		    g.drawString ("STARTING IN " + i, 250, 150);
		    wait (1000);
		}
		g.setColor (Color.white);
		g.fillRect (220, 110, 400, 50);
		g.setColor (Color.black);
		g.drawRect (220, 110, 400, 50);

		g.drawString ("GOOO !!!", 250, 150);
		g.setFont (new Font ("TimesRoman", Font.PLAIN, 20));

		time = 10;
		gameRunning = true;
		QUIT.setEnabled (true);
		QUIT.setVisible (true);
		repaint ();
	    }

	    else if (time > -1) //Game in progress
	    {
		if (questionMode != 0)
		    random ();

		Question (g); //Entire question method

		g.setFont (new Font ("TimesRoman", Font.PLAIN, 25));
		g.drawString ("Time left: " + (int) (time / 10) + "." + (String.valueOf (time)).charAt (String.valueOf (time).length () - 1), 600, 100);
		g.drawString ("Score:" + currentScore, 600, 160);

		A.setEnabled (true);
		A.setVisible (true);
		B.setEnabled (true);
		B.setVisible (true);
		C.setEnabled (true);
		C.setVisible (true);
		D.setEnabled (true);
		D.setVisible (true);
		E.setEnabled (true);
		E.setVisible (true);
		time--;
		wait (100);
		repaint ();
	    }
	    else if (time == -1)
	    {
		String name = "";
		try
		{
		    insert (name, currentScore);
		}
		catch (Exception e)
		{
		}
		time--;
		repaint ();
	    }
	    else //Current game finished, go back to challenge screen general
	    {
		turnOn ();
		currentScreen = 5;
		gameRunning = false;
		
		 System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
		 
		if (!name.getText ().equals (""))
		{
		    if (currentScore < 0)
		    System.out.println ("Are you sure you looked at the lessons, " + name.getText () + "?\nYour score was : " + currentScore + "\n");
		    else if (currentScore < 500)
		    System.out.println ("Not bad for your first try " + name.getText () + "...\nYour score was : " + currentScore + "\n");
		    else if (currentScore < 1000)
		    System.out.println ("Good job " + name.getText () + "\nYour score was : " + currentScore + "\n");
		    else if (currentScore < 2000)
		    System.out.println ("Holy Moly, great work " + name.getText () + "\nYour score was : " + currentScore + "\n");
		    else 
		    System.out.println ("Get a life, " + name.getText () + "\nYour score was : " + currentScore + "\n");
		}
		else
		    System.out.println ("Your score was " + currentScore + ", but you do not have a name. \nPlease enter a name in the textbox, and press enter to save");
		    repaint ();
	    }
	}
    }



    public void actionPerformed (ActionEvent evt)  /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    {
	//Button listeners
	if (evt.getSource () == ButtonMenu)
	{
	    currentScreen = 1;
	    repaint ();
	}
	if (evt.getSource () == ButtonHelp)
	{
	    currentScreen = 2;
	    repaint ();
	}
	if (evt.getSource () == ButtonCredits)
	{
	    currentScreen = 3;
	    repaint ();
	}
	if (evt.getSource () == ButtonLesson)
	{
	    currentScreen = 4;
	    repaint ();
	}
	if (evt.getSource () == ButtonChallenge)
	{
	    currentScreen = 5;
	    repaint ();
	}
	if (evt.getSource () == ButtonNext)
	{
	    if (pageNumber < 4)
	    {
		pageNumber++;
		repaint ();
	    }
	    else
		pageNumber = 4;
	}
	if (evt.getSource () == ButtonPrevious)
	{
	    if (pageNumber > 1)
	    {
		pageNumber--;
		repaint ();
	    }
	    else
		pageNumber = 1;
	}
	if (evt.getSource () == ButtonEasy)
	{
	    difficulty = 1;
	    showStats = false;
	    showLeaderboard = false;
	    repaint ();
	}
	if (evt.getSource () == ButtonMedium)
	{
	    difficulty = 2;
	    showStats = false;
	    showLeaderboard = false;
	    repaint ();
	}
	if (evt.getSource () == ButtonHard)
	{
	    difficulty = 3;
	    showStats = false;
	    showLeaderboard = false;
	    repaint ();
	}
	if (evt.getSource () == ButtonStats)
	{
	    if (showStats == true)
		showStats = false;
	    else if (showLeaderboard = true)
	    {
		showLeaderboard = false;
		showStats = true;
	    }
	    else
		showStats = true;
	    repaint ();
	}
	if (evt.getSource () == ButtonLeaderboard)
	{
	    if (showLeaderboard == true)
		showLeaderboard = false;
	    else if (showStats = true)
	    {
		showStats = false;
		showLeaderboard = true;
	    }
	    else
		showLeaderboard = true;
	    repaint ();
	}
	if (evt.getSource () == PLAY)
	{
	    currentScreen = 6;
	    repaint ();
	}
	if (evt.getSource () == QUIT)
	{
	    wait (1000);
	    time = 0;
	    currentScreen = 5;
	    gameRunning = false;
	    turnOn ();
	    repaint ();
	}
	if (evt.getSource () == A)
	{
	    selectedAnswer = "A";
	    Check ();
	    if (correctAnswer.equals ("A"))
	    {
		questionMode = 1;
	    }
	    else
	    {
		questionMode = 2;
	    }
	    repaint ();
	}
	if (evt.getSource () == B)
	{
	    selectedAnswer = "B";
	    Check ();
	    if (correctAnswer.equals ("A"))
	    {
		questionMode = 1;
	    }
	    else
	    {
		questionMode = 2;
	    }
	    repaint ();
	}
	if (evt.getSource () == C)
	{
	    selectedAnswer = "C";
	    Check ();
	    if (correctAnswer.equals ("A"))
	    {
		questionMode = 1;
	    }
	    else
	    {
		questionMode = 2;
	    }
	    repaint ();
	}
	if (evt.getSource () == D)
	{
	    selectedAnswer = "D";
	    Check ();
	    if (correctAnswer.equals ("A"))
	    {
		questionMode = 1;
	    }
	    else
	    {
		questionMode = 2;
	    }
	    repaint ();
	}
	if (evt.getSource () == E)
	{
	    selectedAnswer = "E";
	    Check ();
	    if (correctAnswer.equals ("A"))
	    {
		questionMode = 1;
	    }
	    else
	    {
		questionMode = 2;
	    }
	    repaint ();
	}

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////          METHODS          //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void wait (int miliseconds)
    {
	//wait in miliseconds
	try
	{
	    Thread.sleep (miliseconds);
	}
	catch (Exception e)
	{
	}
    }


    public static void turnOff ()
    {
	//Turn main buttons
	ButtonHelp.setEnabled (false);
	ButtonCredits.setEnabled (false);
	ButtonLesson.setEnabled (false);
	ButtonChallenge.setEnabled (false);
	ButtonMenu.setEnabled (false);
    }


    public static void turnOn ()
    {
	//Turn on main buttons
	ButtonHelp.setEnabled (true);
	ButtonCredits.setEnabled (true);
	ButtonLesson.setEnabled (true);
	ButtonChallenge.setEnabled (true);
	ButtonMenu.setEnabled (true);
    }


    public static void topScores () throws IOException
    {
	//Load top scores
	try
	{
	    BufferedReader reader = new BufferedReader (new FileReader ("Leaderboard.txt"));
	    String line = "";
	    int count = 0;
	    while ((line = reader.readLine ()) != null)
	    {
		topScorePlayer [count] = line;
		line = (reader.readLine ());
		topScoreScore [count] = Double.parseDouble (line);
		count++;
	    }
	}
	catch (Exception e)
	{
	}
    }


    public static void insert (String name, int score) throws IOException //=====4uvygw3k47tnkw47tvnw348l7tvn NEED TO COMPLETE THIS <---------------------
    {
	//Insert new top score

	BufferedReader reader2 = new BufferedReader (new FileReader ("Leaderboard.txt"));
	BufferedWriter writer2 = new BufferedWriter (new FileWriter ("Leaderboard.txt"));
	String line = "";
	int count = 0;
	while ((line = reader2.readLine ()) != null)
	{
	    topScorePlayer [count] = line;
	    line = (reader2.readLine ());
	    topScoreScore [count] = Double.parseDouble (line);
	    count++;
	}
	writer2.close ();
    }


    public static void Check ()
    {
	//Checks answer and gives/takes points accordingly
	if (selectedAnswer.equals (correctAnswer))
	{
	    currentScore += 5 + currentStreak;
	    currentStreak += difficulty * 2;
	}
	else
	{
	    currentScore -= 3 * difficulty;
	    currentStreak = 0;
	}

    }


    public static void Question (Graphics g)
    {
	//Generate a question
	Random generator = new Random ();
	int randomPosition;

	//4 Default question TEMPLATES

	if (cycle == 0) //Find base
	{
	    g.setFont (new Font ("TimesRoman", Font.BOLD, 20));
	    g.drawString ("Solve for x: x^" + num2 + " = " + num3, 250, 300);
	    displayAnswers (num1, fakeNum1, fakeNum2, fakeNum3, fakeNum4, g);
	}
	else if (cycle == 1) //Find answer of exponent
	{
	    g.setFont (new Font ("TimesRoman", Font.BOLD, 20));
	    g.drawString ("Solve for x: " + num1 + "^" + num2 + " = x", 250, 300);
	    displayAnswers (num1, fakeNum1, fakeNum2, fakeNum3, fakeNum4, g);
	}
	else if (cycle == 2) //Find answer of radical
	{
	}
	else if (cycle == 3) //Find exponent number
	{
	}
    }


    public static void random ()
    {
	//Generates random values in random fashions
	Random generator = new Random ();

	//Generate a random position to place the correct answer in
	randomPosition = generator.nextInt (5) + 1;
	num3 = 1;
	if (difficulty == 1)
	{
	    num1 = generator.nextInt (17);
	    num2 = 2;
	}
	else if (difficulty == 2)
	{
	    num1 = generator.nextInt (10);
	    num2 = generator.nextInt (3) + 2;
	}
	else
	{
	    num1 = generator.nextInt (12);
	    num2 = generator.nextInt (6) + 2;
	}


	for (int i = 0 ; i < num2 ; i++)
	{
	    num3 = num3 * num1;
	}

	//Makes sure not to make repeating numbers
	do
	{
	    fakeNum1 = generator.nextInt (7 * difficulty) + 1;
	}
	while (fakeNum1 == num1 || fakeNum1 == num2 || fakeNum1 == num3);

	do
	{
	    fakeNum2 = generator.nextInt (7 * difficulty) + 1;
	}
	while (fakeNum2 == num1 || fakeNum2 == num2 || fakeNum2 == num3 || fakeNum2 == fakeNum1);

	do
	{
	    fakeNum3 = generator.nextInt (7 * difficulty) + 1;
	}
	while (fakeNum3 == num1 || fakeNum3 == num2 || fakeNum3 == num3 || fakeNum3 == fakeNum2 || fakeNum3 == fakeNum1);

	do
	{
	    fakeNum4 = generator.nextInt (7 * difficulty) + 1;
	}
	while (fakeNum4 == num1 || fakeNum4 == num2 || fakeNum4 == num3 || fakeNum4 == fakeNum3 || fakeNum4 == fakeNum2 || fakeNum4 == fakeNum1);

	//New question generated, set question to unanswered
	questionMode = 0;
    }


    public static void displayAnswers (int x, int x2, int x3, int x4, int x5, Graphics g)
    {
	g.setFont (new Font ("TimesRoman", Font.BOLD, 20));

	//x is the answer, the rest are the random numbers that are incorrect
	if (cycle == 0)
	{
	    if (randomPosition == 1)
	    {
		g.drawString ("A: " + x + "          B: " + x2 + "          C: " + x3 + "          D: " + x4 + "          E: " + x5, 250, 350);
		correctAnswer = "A";
	    }
	    else if (randomPosition == 2)
	    {
		g.drawString ("A: " + x2 + "          B: " + x + "          C: " + x3 + "          D: " + x4 + "          E: " + x5, 250, 350);
		correctAnswer = "B";
	    }
	    else if (randomPosition == 3)
	    {
		g.drawString ("A: " + x2 + "          B: " + x3 + "          C: " + x + "          D: " + x4 + "          E: " + x5, 250, 350);
		correctAnswer = "C";
	    }
	    else if (randomPosition == 4)
	    {
		g.drawString ("A: " + x2 + "          B: " + x3 + "          C: " + x4 + "          D: " + x + "          E: " + x5, 250, 350);
		correctAnswer = "D";
	    }
	    else
	    {
		g.drawString ("A: " + x2 + "          B: " + x3 + "          C: " + x4 + "          D: " + x5 + "          E: " + x, 250, 350);
		correctAnswer = "E";
	    }
	}
	else if (cycle == 1)
	{
	}
	else if (cycle == 2)
	{
	}
	else if (cycle == 3)
	{
	}

    }
}
