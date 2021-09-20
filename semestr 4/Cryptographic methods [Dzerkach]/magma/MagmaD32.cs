namespace magma
{
    internal class MagmaD32 : Converter
    {
        private readonly uint[] _key;
        private readonly ulong[] _data;
        private const uint BlockSize = 8;
        public byte[] GetDecryptData { get; }

        public MagmaD32(byte[] data, byte[] key)
        {
            _key = GetUIntKeyArray(key);
            _data = GetULongDataArray(data);
            GetDecryptData = ConvertToByte(DecryptFile());
        }

        private ulong[] DecryptFile()
        {
            var basicStep = new BasicStep[BlockSize];
            var decryptData = new ulong[_data.Length];

            for (var z = 0; z < _data.Length; z++)
            {
                decryptData[z] = _data[z];

                for (var i = 0; i < basicStep.Length; i++)
                {
                    basicStep[i] = new BasicStep(decryptData[z], _key[i]);
                    decryptData[z] = basicStep[i].BasicEncrypt(false);
                }

                for (var j = 0; j < 3; j++)
                {
                    for (var i = basicStep.Length - 1; i >= 0; i--)
                    {
                        basicStep[i] = new BasicStep(decryptData[z], _key[i]);
                        decryptData[z] = basicStep[i].BasicEncrypt(((j == 2) && (i == 0)));
                    }
                }
            }

            return decryptData;
        }
    }
}