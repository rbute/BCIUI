classdef bot
    %bot
    % syntax: bot(comport,BaudRate,CommandMap)
    % comport: 'COM1' | 'COM2' | '/dev/ttyUSB0' ... etc
    % BaudRate: Standard Baudrates. By default: 9600
    % Command Map: Command Interpretation to bot for
    % bot.sendCommand(Command)   ... Function.
    % Should be cell with two columns
    %
    %
    
    properties
        connection
        commandmap
        oncleanup
    end
    
    methods
        function this=bot(comport_or_struct,BaudRate,CommandMap)
            %             if isstruct(comport_or_struct)
            %                 if isfield()
            
            if false
            else
                this.connection=serial(comport_or_struct);
                
                if ~isempty(BaudRate)
                    disp(['Setting Baud to: ' num2str(BaudRate)]);
                    this.connection.BaudRate=BaudRate;
                end
                if ~isempty(CommandMap)
                    disp('Setting Command Map: ')
                    this.commandmap=CommandMap;
                else
                    disp('Command Map is set Default. ')
                    this.commandmap={'1' '2','3','4','5';...
                        '0' '1' '3' '4' '2' }';
                end
                fopen(this.connection);
                this.oncleanup=onCleanup(@()clear(this));
            end
        end
        
        function send(this,message)
            fprintf(this.connection,message);
        end
        
        function sendCommand(this,command)
            fprintf(this.connection,this.commandmap...
                {find(strcmp(this.commandmap(:,1),command)),2});
        end
        
        function clear(this)
            this.send('0');
            fclose(this.connection);
        end
        
    end
    
end

