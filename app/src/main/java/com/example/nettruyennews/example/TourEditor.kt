package com.example.nettruyennews.example
//
//class TourEditor : ConstraintLayout {
//
//    private var listener: TourEditorListener? = null
//
//    val binding: TourEditorBinding
//    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
//    var onLookTour: () -> Unit = {}
//    var departure: Calendar? = null
//    var idSearchPlace: String? = null
//    var onSearchListener: (query: String,id: String?, departure: Calendar?) -> Unit = { _, _,_ -> }
//
//    constructor(context: Context) : this(context, null)
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//        binding = TourEditorBinding.inflate(inflater, this, true)
//        binding.dateDepartureBt.setOnClickListener {
//            KeyboardUtils.hideSoftInput(this)
//            it.disableFor1Sec()
//            showPicketDateDialog { _it ->
//                val format = if (context.getString(R.string.ticket_language) == "vi") {
//                    "dd/MM/yyyy"
//                } else {
//                    "MM/dd/yyyy"
//                }
//                val dateFormat = DateUtils.formatTime(format, _it.timeInMillis)
//                departure = _it
//                binding.dateDepartureBt.text = dateFormat
//            }
//        }
//        binding.searchBt.setOnClickListener {
//            it.disableFor1Sec()
//            onSearchListener(binding.nameTv.text.toString(),idSearchPlace, departure)
//        }
//        binding.lookComplexBt.setOnClickListener {
//            it.disableFor1Sec()
//            listener?.searchTourGeneral()
//        }
//        binding.lookTourBt.setOnClickListener {
//            it.disableFor1Sec()
//            onLookTour()
//        }
//        binding.nameTv.setOnClickListener {
//            it.disableFor1Sec()
//            listener?.searchPlaceTour()
//        }
//    }
//
//    fun setPlaceSearchResult(placeResult: MutableList<String?>) {
//        binding.nameTv.setText(placeResult[0])
//        idSearchPlace = placeResult[1]
//    }
//
//    private fun showPicketDateDialog(result: (date: Calendar) -> Unit) {
//        val picker = PickerDateDialog.onCreate(binding.root.context)
//            .setPickerDateMode(PickerDateDialog.MODE.SINGLE)
//            .setDurationYearEnable(5)
//            .build()
//        picker.setOnSingleChangedListener { _startDate ->
//            result(_startDate)
//        }
//        picker.show()
//    }
//
//    fun setListener(listener: TourEditorListener?) {
//        this.listener = listener
//    }
//}
