Here goes skins.

Skins contains images, css for GUI and background and data for creating playground used by PaintableFactory.

Structure:
Skin_Folder                 //Name of skin
|   skin.css                //Css for GUI and background
|   skin.xml                //Skin configuration
|
+---Figures                 //Images of figures
|       bishop.png
|       bishop_promoted.png
|       gold_general.png
|       king.png
|       knight.png
|       knight_promoted.png
|       lance.png
|       lance_promoted.png
|       pawn.png
|       pawn_promoted.png
|       rook.png
|       rook_promoted.png
|       silver_general.png
|       silver_general_promoted.png
|
+---GUI                     //GUI images(in css)
|   +---Background
|   |       background.png
|   |
|   \---Button
|           button.png
|           button_clicked.png
|           button_hovered.png
|
\---Playground              //Playground images
    |   table.png           //background(in css)
    |
    \---Cell                //cell states
            enemy.png
            highlighted.png
            normal.png