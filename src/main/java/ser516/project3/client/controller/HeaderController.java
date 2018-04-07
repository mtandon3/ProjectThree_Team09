package ser516.project3.client.controller;

import ser516.project3.client.view.HeaderView;
import ser516.project3.interfaces.ControllerInterface;
import ser516.project3.model.ConnectionPopUpModel;
import ser516.project3.model.HeaderModel;
import ser516.project3.server.controller.ServerController;
import ser516.project3.utilities.ControllerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class controls the UI for the header view on
 * client which helps in connection and shows the
 * connection status.
 *
 * @author Adhiraj Tikku, Vishakha Singal
 *
 */

public class HeaderController implements ControllerInterface {

  private HeaderView headerView;
  private HeaderModel headerModel;
  private ServerController serverController;
  private ConnectionPopUpController connectionPopUpController;
  private ConnectionPopUpModel connectionPopUpModel;
  

  /**
   * Constructor overloaded to initiate the model and view as well
   *
   * @param headerModel
   * @param headerView
   */
  public HeaderController(HeaderModel headerModel, HeaderView headerView) {
    connectionPopUpModel = new ConnectionPopUpModel();
    this.headerView = headerView;
    this.headerModel = headerModel;
  }

  @Override
  public void initializeView() {
    headerView.initializeView(null);
    headerView.addConnectButtonListener(new ConnectListener());
    headerView.addServerOpenButtonListener(new ServerOpenListener());
  }
  
  class ServerOpenListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(serverController == null) {
		  ControllerFactory controllerFactory = new ControllerFactory();
          serverController = (ServerController) controllerFactory.getController("SERVER", null, null, null);
          serverController.initializeView();
		} else {
		  serverController.showServer();
		}
	}
	  
  }

  class ConnectListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (headerModel.isConnectionStatus()) {
        ClientController.getInstance().toggleConnectionToServer(null, 0);
      } else {
        connectionPopUpController = new ConnectionPopUpController(connectionPopUpModel);
      }
    }
  }

  public HeaderView getHeaderView() {
    return headerView;
  }

  public void setConnectionStatus(boolean connectionStatus) {
    headerModel.setConnectionStatus(connectionStatus);
    headerView.updateConnectionLabel();
  }

  public void setHeaderTimeStamp(double timeStamp) {
  	headerModel.setTimeStamp(timeStamp);
  	headerView.updateTimeStamp();
  }
}
