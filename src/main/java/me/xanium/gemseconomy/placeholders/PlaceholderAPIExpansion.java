package me.xanium.gemseconomy.placeholders;

import me.xanium.gemseconomy.GemsEconomy;
import me.xanium.gemseconomy.account.Account;
import me.xanium.gemseconomy.account.AccountManager;
import me.xanium.gemseconomy.currency.Currency;
import me.xanium.gemseconomy.currency.CurrencyManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

    private final CurrencyManager currencyManager;
    private final AccountManager accountManager;

    public PlaceholderAPIExpansion(GemsEconomy plugin) {
        this.currencyManager = plugin.getCurrencyManager();
        this.accountManager = plugin.getAccountManager();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "geco";
    }

    @Override
    public @NotNull String getAuthor() {
        return "HauserGrim";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    // %balance_<[currency]|default><|_formatted>%
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) return "";

        String[] split = params.split("_");
        if (split[0].equals("balance")) {
            if (split.length == 2 || (split.length == 3 && split[2].equals("formatted"))) {
                Account account = accountManager.getAccount(player.getUniqueId());
                Currency currency = split[1].equals("default") ? currencyManager.getDefaultCurrency() : currencyManager.getCurrency(split[1]);
                if (split.length == 3) {
                    return currency.format(account.getBalance(currency));
                }
                return String.valueOf(account.getBalance(currency));
            }
        }
        return "";
    }
}
