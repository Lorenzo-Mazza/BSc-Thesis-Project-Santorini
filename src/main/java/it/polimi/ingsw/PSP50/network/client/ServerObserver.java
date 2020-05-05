package it.polimi.ingsw.PSP50.network.client;

public interface ServerObserver
{
  void didReceiveConvertedString(String oldStr, String newStr);
}
