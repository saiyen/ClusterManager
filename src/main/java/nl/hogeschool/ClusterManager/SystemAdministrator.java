package nl.hogeschool.ClusterManager;

import ContainerManager.ContainerManagerFactory;
import Interfaces.ContainerManager;
import Interfaces.DataFormatType;
import Models.ServerModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SystemAdministrator {
        public ContainerManager containerManager;
        DataFormatType dataFormatType;
	String name;
        
        public SystemAdministrator(String useContainerType){
            ContainerManagerFactory containerManagerFactory = new ContainerManagerFactory();
            containerManager = containerManagerFactory.getContainerManager(useContainerType);
        }
        
	public void setDataFormatStrategy(DataFormatType dataFormatType)
	{
		this.dataFormatType = dataFormatType;
	}

        //use the strategy
        public void useStrategyToFormatData(List<ServerModel> servers) {
            dataFormatType.convertToDataFormat(servers);
        }
}
