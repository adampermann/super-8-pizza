package com.superpizza.inventory;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class InventoryControllerTest
{
    InventoryRepository repo = mock(InventoryRepository.class);
    InventoryController controller = new InventoryController(repo);

    @Test
    private void ok() throws InterruptedException
    {
        Map<String, InventoryItem> inventory = new HashMap<>();

        when(repo.saveInventory(inventory)).thenReturn(true);
        when(repo.getInventory(controller)).thenReturn(inventory);
    }

}
