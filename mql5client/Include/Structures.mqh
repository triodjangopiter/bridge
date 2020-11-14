#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Enums.mqh>

struct HttpResponse
  {
   int               status;
   string            body;
  };

struct HttpRequest
  {
   string            url;
   string            body;
   string            headers;
  };

struct RestConfig
  {
   string            host;
   int               port;
   string            headers;
   int               timeout;
  };

struct Signal
  {
   long              positionId;
   string            advisorId;
   PositionType      type;
   double            lot;
   int               stopLoss;
   int               takeProfit;
  };

struct Position
  {
   bool              isHistory;
   PositionType      type;
   long              magic;
   long              positionId;
   double            lot;
   int               stopLoss;
   int               takeProfit;
   long              openAt;
   long              closeAt;
   double            profit;
  };