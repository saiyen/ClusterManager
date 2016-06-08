package Interfaces;

import Models.ContainerModel;
import java.util.List;

public interface IDataFormatChooser<T> {
    T convertToDataFormat(List<ContainerModel> servers);
}
