package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView{

    final VirtualServer server;
    private Game game;
    private GameController gameController;
    public RmiClient(VirtualServer server) throws RemoteException{
        this.server = server;
    }

    private void run() throws Exception{
        this.server.connect(this);
        this.runCli();
    }

    private void runCli() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean nicknameChosen = false;
        do{
            if(!nicknameChosen){
                String nickname;
                do{
                    System.out.println("Choose your Nickname\n");
                    nickname = scan.nextLine();
                } while(!server.checkNicknameValidity(nickname));
                nicknameChosen = true;
                gameController = server.addPlayerToGame(nickname);
                game = gameController.getGame();
                System.out.println("You have joined the game "+game.getIdGame()+"\n");
            }
            if(game.getSize()==1){
                boolean validSize = false;
                int gameSize;
                do {
                    System.out.println("The game size is 1, for allowing other players to join, choose the game's size:\n");
                    gameSize = scan.nextInt();
                    try {
                        server.updateSize(gameSize, game.getIdGame());
                        validSize = true;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (!validSize);
            }
            String nextAction = "done";
            while(!game.getStatus().equals(GameStatus.ENDED) && nextAction.equals("done")){
                nextAction = scan.nextLine();
                switch(nextAction){
                    // All the choice could be done using text input, for example:
                    // >> selectObjective
                    // "obj1, obj2"
                    // >> 1
                    // "You chose obj1"
                    // ...
                }
                nextAction = "done";
            }
            System.out.println("\n---END OF CURRENT GAME DEV---\n");
        }while (!game.getStatus().equals(GameStatus.ENDED));
    }
    public static void main(String[] args) throws Exception {
        final String serverName = "Server";
        Registry registry = LocateRegistry.getRegistry("localhost",2222);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    @Override
    public void updatePlayerJoined(String newPlayer) throws RemoteException {
        System.err.println("[EVENT] "+newPlayer+" joined the game");
    }

    public void updateSizeChanged(int size) throws RemoteException {
        System.err.println("[EVENT] game size changed to "+size);
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }
}