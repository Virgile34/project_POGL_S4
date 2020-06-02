# project_POGL_S4
jeu_ile_iterdite


Présentation du jeu : Ile Interdite.


Lors du lancement du jeu, nous vous proposons de choisir la taille de l'île, le nmbre de joueur et la difficulté du jeu. Lorsque vous avez tout séléctionné, vous voilà sur l'île interdite. 

Le système de déplacement d'effectue à l'aide des flèches affichées à l'écran. Durant vote tour de jeu, vous pouvez faire jusqu'à 3 actions parmis assécher, se déplacer ou récupérer un artefact. 
L'assèchement d'une zone adjacente se fait également à l'aide des flèches affichées à l'écran : flèche gauche pour inonder la zone à votre gauche etc. 

Lorsque vous finissez votre tour, vous allez aléatoirement recevoir ou non un clef, qui va vous permettre par la suite de récupérer un artefact.

Les couleurs des zones d'artefact sont :
vert -> terre //
rouge -> feu //
bleu -> eau //
jaune -> air //
et l'héliport est représenté par un H noir. 

Si vous cliquez malencontreusement sur la case assécher mais que vous ne voulez plus assècher, vous pouvez cliquer sur une zone normal (non inondé) cela n'affectera pas votre nombre d'action restante.

Pour coder ce jeu, nous avons suivi le principe du Modele-Vue-Controleur (MVC). Le modèle gère le jeu en fond, la vue gère l'interface et le controleur gère l'interaction avec l'utilisateur.

Nous avons répartie les tâches de la façon suivante :
Virgile s'est principalement occupé de la gestion du modèle.

Garance s'est principalement occupé du controleur. Quelles actions les JButton engendrent etc.

Mathis s'est principalement occupé de la vue. L'affichage graphique, l'utilisation des Jframes etc.

Mais que ce soit pour aider un membre de projet ou tout simplement pour mieux comprendre le projet, nous nous sommes tous penché sur chaque face du projet.
