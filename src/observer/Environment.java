package observer;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.Modele;

public class Environment implements Observer {
    /**
     * JFrame est une classe fournie par Swing. Elle représente la fenêtre
     * de l'application graphique.
     */
    private JFrame frame;
    private JFrame beginFrame;
    private JFrame endFrame;

    private JPanel commandes_info;
    /**
     * VueGrille et VueCommandes sont deux classes définies plus loin, pour
     * nos deux parties de l'interface graphique.
     */
    private VueModele  ileGraphique;
    private VueCommandes commandes;
    private VueInformation info;

    private int m;
    private int n;
    private int nbPlayers;
    private float lvl;
    private Modele jeu;

    /** Construction d'une vue attachée à un modèle. */
    public Environment(){
        this.init_beginFrame();
    }


    public Environment(Modele jeu){
        this.init_Frame(jeu);
        m = jeu.getLine();
        n = jeu.getCol();
        nbPlayers = jeu.getJoueurs().size();
        lvl = jeu.level;
    }



    private void reset(){
        if (this.beginFrame != null) this.beginFrame.setVisible(false);
        if (this.frame != null) this.frame.setVisible(false);
        if (this.endFrame != null) this.endFrame.setVisible(false);
    }
    


    private void init_Frame(Modele ile) {
        this.reset();
        this.jeu = ile;
        this.jeu.addObserver((Observer) this);


        this.frame = new JFrame("Modele de l'Île Interdite");
        frame.setBackground(Color.BLACK);
        /**
         * On précise un mode pour disposer les différents éléments à
         * l'intérieur de la fenêtre. Quelques possibilités sont :
         *  - BorderLayout (défaut pour la classe JFrame) : chaque élément est
         *    disposé au centre ou le long d'un bord.
         *  - FlowLayout (défaut pour un JPanel) : les éléments sont disposés
         *    l'un à la suite de l'autre, dans l'ordre de leur ajout, les lignes
         *    se formant de gauche à droite et de haut en bas. Un élément peut
         *    passer à la ligne lorsque l'on redimensionne la fenêtre.
         *  - GridLayout : les éléments sont disposés l'un à la suite de
         *    l'autre sur une grille avec un nombre de lignes et un nombre de
         *    colonnes définis par le programmeur, dont toutes les cases ont la
         *    même dimension. Cette dimension est calculée en fonction du
         *    nombre de cases à placer et de la dimension du contenant.
         */
        frame.setLayout(new GridLayout(1, 2));
    
        /**Définition des joueurs**/

        /** Définition des trois vues et ajout à la fenêtre. */
        this.ileGraphique = new VueModele(this.jeu);
        frame.add(this.ileGraphique);


        this.commandes = new VueCommandes(this.jeu);
        this.info = new VueInformation(this.jeu);

        this.commandes_info = new JPanel(new GridLayout(2, 1));
        commandes_info.add(this.info);
        commandes_info.add(this.commandes);

        frame.add(commandes_info);
        /**
         * Remarque : on peut passer à la méthode [add] des paramètres
         * supplémentaires indiquant où placer l'élément. Par exemple, si on
         * avait conservé la disposition par défaut [BorderLayout], on aurait
         * pu écrire le code suivant pour placer la grille à gauche et les
         * commandes à droite.
         *     frame.add(grille, BorderLayout.WEST);
         *     frame.add(commandes, BorderLayout.EAST);
         */

        /**
         * Fin de la plomberie :
         *  - Ajustement de la taille de la fenêtre en fonction du contenu.
         *  - Indiquer qu'on quitte l'application si la fenêtre est fermée.
         *  - Préciser que la fenêtre doit bien apparaître à l'écran.
         */
        this.frame.setSize(2 * (this.jeu.getCol()+1) * this.ileGraphique.TAILLE, 
                (this.jeu.getCol()+1) * this.ileGraphique.TAILLE);
        // frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        // solution pour regle le bug de si on appuye par sur switch en 1er, il ne se
        // dessinera pas qui marche meme pas lol
        // this.jeu.getControleur().bouton_echange();
        // this.jeu.getControleur().boutonFDT();
    }

    public void init_beginFrame (){
        this.reset();

        /**
         * Environement du debut. Lorsque tout les paramètres sont entré, on lance le jeu!
         */
        m=0;
        n=0;
        nbPlayers=0;
        lvl=0;

        beginFrame = new JFrame("Modele de l'Île Interdite");
        beginFrame.setBackground(Color.BLACK);
        beginFrame.setLayout(new FlowLayout());
        beginFrame.setSize(600, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        JTextField t = new JTextField(15);

        String[] taille_map= {"?","4", "6", "8", "10"};
        JLabel label_taille = new JLabel("Choississez la taille de la carte");
        JComboBox combo_taille = new JComboBox(taille_map);

        String[] NbJoueurs= {"?","1", "2", "3", "4"};
        JLabel label_joueur = new JLabel("Choississez le nombre de joueurs");
        JComboBox combo_joueurs = new JComboBox(NbJoueurs);

        String[] level= {"?","1", "2", "3"};
        JLabel label_level = new JLabel("Choississez le niveau");
        JComboBox combo_level = new JComboBox(level);

        JButton Valide = new JButton("Je valide");


        /**
         * On ajoute au JFrame tout les composantes que nous avons crée (JPanel, JLabel, JComboBox etc.)
         */
        /**
         * Pour le choix de la taille de la map
         */
        panel.add(combo_taille);
        panel.add(label_taille);

        /**
         * Pour le choix du nombre de joueurs
         */
        panel.add(combo_joueurs);
        panel.add(label_joueur);

        /**
         * Pour le choix du level
         */
        panel.add(combo_level);
        panel.add(label_level);

        panel.add(Valide);

        combo_taille.addActionListener(e -> {
            int index = combo_taille.getSelectedIndex();
            switch (index){
                case 1:
                    m=4;
                    n=4;
                    break;
                case 2:
                    m=6;
                    n=6;
                    break;
                case 3:
                    m=8;
                    n=8;
                    break;
                case 4:
                    m=10;
                    n=10;
                    break;
                default:
                    m=0;
                    n=0;
            }
        });
        combo_joueurs.addActionListener(e -> { nbPlayers=combo_joueurs.getSelectedIndex(); });
        combo_level.addActionListener(e -> { 
            int index = combo_level.getSelectedIndex();
            switch (index){
                case 1: this.lvl = 0.1f;
                case 2: this.lvl = 0.3f;
                case 3: this.lvl = 0.6f;
                default:
            }
        });

        Valide.addActionListener(e -> {
            if(m!=0 && n!=0 && nbPlayers!=0 && lvl!=0){
                Modele jeu = new Modele(m, n, nbPlayers, lvl);
                System.out.println(jeu.toString());
                this.init_Frame(jeu);
            }
        });




        beginFrame.getContentPane().add(panel);
        beginFrame.setLocationRelativeTo(null);
        beginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        beginFrame.setVisible(true);


    }

    public void set_endFrame(String s){
        /**
         * fenetre de fin
         */
        // if (this.beginFrame != null) this.beginFrame.setVisible(false);


        this.endFrame = new JFrame("Modele de l'Île Interdite");
        this.endFrame.setBackground(Color.BLACK);
        this.endFrame.setLayout(new FlowLayout());
        this.endFrame.setSize(600, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,3));


        JLabel label_perdu = new JLabel(s);
        JButton boutonRetry = new JButton("Reessayer");
        JButton boutonReset = new JButton("Nouvelle partie");
        JButton boutonQuit = new JButton("Quitter");

        boutonRetry.addActionListener(e -> {
            this.init_Frame(new Modele(m, n, nbPlayers, lvl));
        });
        boutonReset.addActionListener(e -> {
            this.init_beginFrame();
        });
        boutonQuit.addActionListener(e -> {
            System.exit(0);
        });

        panel.add(new empty());
        panel.add(label_perdu);
        panel.add(new empty());
        panel.add(boutonRetry);
        panel.add(boutonReset);
        panel.add(boutonQuit);




        this.endFrame.getContentPane().add(panel);
        this.endFrame.setLocation(this.frame.getLocation().x, this.frame.getLocation().y + this.frame.getSize().height);
        // this.endFrame.setLocationRelativeTo(this.frame);
        this.endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.endFrame.setVisible(true);


    }

    class empty extends Component {
        @Override
        public void paint(Graphics g) {
        }
    }

    @Override
    public void update() {
        if (!this.jeu.getControleur().InGame()) { 
            this.set_endFrame(this.jeu.endString);
        }
    }

}

